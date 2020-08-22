package tech.v2c.minecraft.plugins.lode.RESTful.global;

import cn.nukkit.utils.ConfigSection;
import com.google.gson.Gson;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import tech.v2c.minecraft.plugins.lode.Lode;
import tech.v2c.minecraft.plugins.lode.tools.EncryptUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BaseHttpServer extends NanoHTTPD {
    public static BaseHttpServer instance;

    public BaseHttpServer() {
        super(Lode.instance.getConfig().getSection("Server").getInt("HttpPort"));
        BaseHttpServer.instance = this;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (!Lode.instance.isDebugMode) {
            if (!uri.toLowerCase().contains("/api/server/getstatus")) {
                String clientAuthStr = session.getHeaders().get("x-lode-authentication");
                // 没有鉴权头
                if (clientAuthStr == null || clientAuthStr.equals("")) {
                    return Response.newFixedLengthResponse(Status.UNAUTHORIZED, MIME_PLAINTEXT, "Error:  401 Unauthorized. 需要鉴权信息.");
                }

                //鉴权头不完整
                if (clientAuthStr.split(";").length != 2) {
                    return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. 鉴权信息不完整.");
                }

                // 鉴权信息格式:
                // token={token}; ts={timestamp}
                try {
                    long ts = Long.parseLong(clientAuthStr.split(";")[1].split("=")[1].trim());
                    if (!CheckTimestamp(ts)) {
                        return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. Timeout.");
                    }

                    String serverAuthStr = GetAuthentication(uri);
                    String token = clientAuthStr.split(";")[0].split("=")[1].trim();
                    if (!serverAuthStr.equalsIgnoreCase(token)) {
                        return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. Authentication failed.");
                    }
                } catch (Exception e) {
                    return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 403 Forbidden. 鉴权信息不正确. " + e.getMessage());
                }
            }
        }

        Map<String, Object> jsonData;

        // 如果是特定的文件上传请求, 把文件放进 Map
        if (uri.toLowerCase().contains("upload") || uri.toLowerCase().contains("install")) {
            jsonData = new HashMap<>();
            Map<String, File> allFile = UploadFiles(session);
            jsonData.put("files", allFile);
        } else {
            // 从 InputStream 获取 Body
            String body = "";
            Integer length = Integer.parseInt(session.getHeaders().get("content-length"));
            try {
                if(length > 0){
                    body = GetBody(session.getInputStream());
                }
            } catch (IOException e) {
                return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 500 INTERNAL ERROR. 请求体不正确. " + e.getMessage());
            }

            // Body => Map
            try {
                jsonData = new Gson().fromJson(body, Map.class);
            } catch (Exception e) {
                return Response.newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, "Error: 500 INTERNAL ERROR. 请求体格式不正确. " + e.getMessage());
            }
        }

        // 调用指定的 action
        String result;
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

    private Map<String, File> UploadFiles(IHTTPSession session) {
        Map<String, String> files = new HashMap<>();
        try {
            session.parseBody(files);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        Map<String, File> allFile = new HashMap<>();

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
        ConfigSection conf = Lode.instance.getConfig().getSection("Server").getSection("Authentication");
        String base = conf.getString("UserName") + url.toLowerCase() + conf.getString("Password");
        return EncryptUtils.EncodeBySHA256(base);
    }

    private boolean CheckTimestamp(long ts) {
        return (Math.abs((System.currentTimeMillis() - ts)) / (1000 * 60)) <= 2;
    }

    private String GetBody(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int count = 0;
        while (count == 0){
            count = inputStream.available();
        }
        byte[] buffer = new byte[count];
        result.write(buffer, 0, inputStream.read(buffer));

        return result.toString("UTF-8");
    }
}
