package tech.v2c.minecraft.plugins.jsonApi.EventNotify.global;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.tools.results.JsonResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class JsonApiWebSocketServer extends WebSocketServer {
    public static HashMap<Object, WebSocket> connPool = new HashMap<Object, WebSocket>();

    public JsonApiWebSocketServer(){
        super(new InetSocketAddress(JsonApi.config.getIP(), JsonApi.config.getWsPort()));
    }

    public static void SendMsg(JsonResult result){
        JsonApiWebSocketServer.connPool.values().forEach(conn -> {
            conn.send(result);
        });
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connPool.put(conn.getDraft(), conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connPool.remove(conn.getDraft());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }
}
