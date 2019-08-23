package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.plugin.PluginBase;

import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.EventNotify.Events.*;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.global.EventManage;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.global.JsonApiWebSocketServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.RouteManage;

import java.io.IOException;

public class JsonApi extends PluginBase {
    public static JsonApi instance;

    private JsonApiWebSocketServer ws;

    private static boolean isEnableWs;

    public JsonApi() {
        JsonApi.instance = this;
    }

    @Override
    public void onEnable() {
        InitPlugin();

        InitActions();
        RouteManage.RegisterRoute();
        getLogger().info("Finish register actions.");
        (new Thread(() -> ServerRunner.run(BaseHttpServer.class))).start();
        getLogger().info("JsonAPI Http Server running at: " + getConfig().getSection("Server").getInt("HttpPort"));

        if(this.isEnableWs){
            InitEvents();
            EventManage.RegisterEventListener();
            getLogger().info("Finish register events.");
            ws  = new JsonApiWebSocketServer();
            (new Thread(() -> ws.start())).start();
            getLogger().info("JsonAPI WebSocket Server running at: " + getConfig().getSection("Server").getInt("WsPort"));
        }
    }

    @Override
    public void onDisable() {
        BaseHttpServer.instance.stop();
        getLogger().info("JsonAPI Http Server is shutdown.");

        if(this.isEnableWs && ws != null){
            try {
                ws.stop();
                getLogger().info("JsonAPI WebSocket Server is shutdown.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void InitActions() {
        RouteManage.allAction.add(UserAction.class);
        RouteManage.allAction.add(ServerAction.class);
        RouteManage.allAction.add(PluginAction.class);
        RouteManage.allAction.add(ItemAction.class);
    }

    private void InitEvents(){
        if(this.isEnableWs){
            EventManage.allEvent.put("ServerCommand", new CommandEvent());
            EventManage.allEvent.put("PlayerChat", new PlayerTalkEvent());
        }
    }

    private void InitPlugin() {
        saveDefaultConfig();

        this.isEnableWs = getConfig().getSection("EventListener").getBoolean("IsEnable");
    }
}