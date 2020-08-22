package tech.v2c.minecraft.plugins.lode;

import cn.nukkit.plugin.PluginBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.lode.EventNotify.Console.ServerConsoleEvent;
import tech.v2c.minecraft.plugins.lode.EventNotify.Events.*;
import tech.v2c.minecraft.plugins.lode.EventNotify.global.EventManage;
import tech.v2c.minecraft.plugins.lode.EventNotify.global.LodeWebSocketServer;
import tech.v2c.minecraft.plugins.lode.RESTful.actions.*;
import tech.v2c.minecraft.plugins.lode.RESTful.global.BaseHttpServer;
import tech.v2c.minecraft.plugins.lode.RESTful.global.RouteManage;
import tech.v2c.minecraft.plugins.lode.tools.FifoList;
import tech.v2c.minecraft.plugins.lode.tools.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Lode extends PluginBase {
    public static Lode instance;

    private LodeWebSocketServer ws;

    private static boolean isEnableWs;

    public static boolean isDebugMode;

    public static int logMaxSize;

    public static FifoList logList;

    public Lode() {
        Lode.instance = this;
    }

    @Override
    public void onEnable() {
        InitPlugin();
        InitActions();

        RouteManage.RegisterRoute();
        LogUtils.Info("Finish register actions.");
        (new Thread(() -> ServerRunner.run(BaseHttpServer.class))).start();
        LogUtils.Info("Lode Http Server running at: " + getConfig().getSection("Server").getInt("HttpPort"));

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

            ws = new LodeWebSocketServer();
            (new Thread(() -> ws.start())).start();
            LogUtils.Info("Lode WebSocket Server running at: " + getConfig().getSection("Server").getInt("WsPort"));
        }
    }

    @Override
    public void onDisable() {
        BaseHttpServer.instance.stop();
        LogUtils.Info("Lode Http Server is shutdown.");

        if (this.isEnableWs && ws != null) {
            try {
                ws.stop();
                LogUtils.Info("Lode WebSocket Server is shutdown.");
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
            LogUtils.Debug("Lode websocket server is not enable.");
        }
    }

    private void InitPlugin() {
        saveDefaultConfig();

        this.isEnableWs = getConfig().getSection("EventListener").getBoolean("IsEnable");
        this.isDebugMode = getConfig().getBoolean("DebugMode");
        this.logMaxSize = getConfig().getInt("LogMaxSize");

        this.logList = new FifoList(logMaxSize);
    }

    private void InitConsoleEvent(){
        Logger logger = (Logger)LogManager.getRootLogger();
        logger.addAppender(new ServerConsoleEvent());
    }
}