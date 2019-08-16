package tech.v2c.minecraft.plugins.jsonApi.RESTful.global;

import cn.nukkit.command.ConsoleCommandSender;
import com.google.gson.Gson;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.tools.EncryptUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseHttpServer extends NanoHTTPD {
    public static final boolean IS_DEBUG = true;
    public static BaseHttpServer instance;

    public BaseHttpServer() throws IOException {
        super(JsonApi.serverPort);
        BaseHttpServer.instance = this;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (!IS_DEBUG) {
            String clientAuthStr = session.getHeaders().get("x-jsonapi-authentication");
            if (clientAuthStr == null) {
                return Response.newFixedLengthResponse(Status.UNAUTHORIZED, MIME_PLAINTEXT, "Error:  401 Unauthorized. Need Authentication.");
            }

            String serverAuthStr = GetAuthentication(uri);
            if (!serverAuthStr.equalsIgnoreCase(clientAuthStr)) {
                return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. Authentication failed.");
            }
        }

        Map<String, List<String>> parameters = session.getParameters();
        JsonData jsonData = new Gson().fromJson(GetQueryString(parameters, "Data"), JsonData.class);

        if(!IS_DEBUG){
            if (!CheckTimestamp(jsonData.TimeStamp)) {
                return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. Timeout.");
            }
        }

        if (uri.toLowerCase().contains("upload") || uri.toLowerCase().contains("install")) {
            Map<String, File> allFile = UploadFiles(session);
            jsonData.Data.put("files", allFile);
        }

        String result = "";
        try {
            result = RouteManage.TouchAction(uri.toLowerCase(), jsonData);
        } catch (Exception e) {
            return Response.newFixedLengthResponse(Status.INTERNAL_ERROR, MIME_PLAINTEXT, "");
        }

        if (result == "404") {
            return Response.newFixedLengthResponse(Status.NOT_FOUND, MIME_PLAINTEXT, "Error: " + uri + " is not found.");
        }

        return Response.newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", result);
    }

    private String GetQueryString(Map<String, List<String>> parameters, String key) {
        if (parameters.containsKey(key)) {
            return parameters.get(key).get(0);
        }
        return null;
    }

    private Map<String, File> UploadFiles(IHTTPSession session) {
        Map<String, String> files = new HashMap<>();
        try {
            session.parseBody(files);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        Map<String, File> allFile = new HashMap<String, File>();

        for (String key : session.getParameters().keySet()) {
            if (key.equalsIgnoreCase("data")) continue;
            final String tmpFilePath = files.get(key);
            final String fileName = key;
            final File tmpFile = new File(tmpFilePath);

            allFile.put(fileName, tmpFile);
        }

        return allFile;
    }

    private String GetAuthentication(String url) {
        String base = JsonApi.userName + url + JsonApi.password;
        return EncryptUtils.EncodeBySHA256(base);
    }

    private boolean CheckTimestamp(long ts) {
        return (Math.abs((System.currentTimeMillis() - ts)) / (1000 * 60)) <= 2;
    }
}
