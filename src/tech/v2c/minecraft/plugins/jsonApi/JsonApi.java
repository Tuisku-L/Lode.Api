package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.plugin.PluginBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.EventNotify.Console.ServerConsoleEvent;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.Events.*;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.global.EventManage;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.global.JsonApiWebSocketServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.RouteManage;
import tech.v2c.minecraft.plugins.jsonApi.tools.LogUtils;

import java.io.IOException;

public class JsonApi extends PluginBase {
    public static JsonApi instance;

    private JsonApiWebSocketServer ws;

    private static boolean isEnableWs;

    public static boolean isDebugMode;

    public JsonApi() {
        JsonApi.instance = this;
    }

    @Override
    public void onEnable() {
        InitPlugin();

        InitActions();
        RouteManage.RegisterRoute();
        LogUtils.Info("Finish register actions.");
        (new Thread(() -> ServerRunner.run(BaseHttpServer.class))).start();
        LogUtils.Info("JsonAPI Http Server running at: " + getConfig().getSection("Server").getInt("HttpPort"));

        if (this.isEnableWs) {
            if(getConfig().getSection("EventListener").getBoolean("OtherEvents")){
                InitEvents();
                EventManage.RegisterEventListener();
                LogUtils.Info("Finish register events.");
            }

            if(getConfig().getSection("EventListener").getBoolean("ServerConsole")){
                InitConsoleEvent();
                LogUtils.Info("Finish register Server Console Event.");
            }

            ws = new JsonApiWebSocketServer();
            (new Thread(() -> ws.start())).start();
            LogUtils.Info("JsonAPI WebSocket Server running at: " + getConfig().getSection("Server").getInt("WsPort"));
        }
    }

    @Override
    public void onDisable() {
        BaseHttpServer.instance.stop();
        LogUtils.Info("JsonAPI Http Server is shutdown.");

        if (this.isEnableWs && ws != null) {
            try {
                ws.stop();
                LogUtils.Info("JsonAPI WebSocket Server is shutdown.");
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

    private void InitEvents() {
        if (this.isEnableWs) {
            EventManage.allEvent.put("ServerCommand", new CommandEvent());
            EventManage.allEvent.put("PlayerChat", new PlayerTalkEvent());
        } else {
            LogUtils.Debug("JsonApi websocket server is not enable.");
        }
    }

    private void InitPlugin() {
        saveDefaultConfig();

        this.isEnableWs = getConfig().getSection("EventListener").getBoolean("IsEnable");
        this.isDebugMode = getConfig().getBoolean("DebugMode");
    }

    private void InitConsoleEvent(){
        Logger logger = (Logger)LogManager.getRootLogger();
        logger.addAppender(new ServerConsoleEvent());
    }
}