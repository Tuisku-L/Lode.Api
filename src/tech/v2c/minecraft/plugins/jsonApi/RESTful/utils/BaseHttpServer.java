package tech.v2c.minecraft.plugins.jsonApi.RESTful.utils;

import com.google.gson.Gson;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.entities.JsonData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseHttpServer extends NanoHTTPD {
    public BaseHttpServer() throws IOException {
        super(30000);
        // start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, List<String>> parameters = session.getParameters();
        JsonData jsonData = new Gson().fromJson(GetQueryString(parameters, "Data"), JsonData.class);
        String uri = session.getUri();

        if(uri.toLowerCase().contains("upload") || uri.toLowerCase().contains("install")){
            Map<String, File> allFile = UploadFiles(session);
            jsonData.Data.put("files", allFile);
        }

        String result = "";
        try{
            result = RouteManage.TouchAction(uri.toLowerCase(), jsonData);
        }catch(Exception e){
            return Response.newFixedLengthResponse(Status.INTERNAL_ERROR, MIME_HTML, "");
        }

        if(result == "404") return Response.newFixedLengthResponse(Status.NOT_FOUND, MIME_HTML, "Error: " + uri + " is not found.");
        return Response.newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", result);
    }

    private String GetQueryString(Map<String, List<String>> parameters, String key){
        if(parameters.containsKey(key)){
            return parameters.get(key).get(0);
        }
        return null;
    }

    private  Map<String, File> UploadFiles(IHTTPSession session){
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
            if(key.equalsIgnoreCase("data")) continue;
            final String tmpFilePath = files.get(key);
            final String fileName = key;
            final File tmpFile = new File(tmpFilePath);

            allFile.put(fileName, tmpFile);
        }

        return allFile;
    }
}
