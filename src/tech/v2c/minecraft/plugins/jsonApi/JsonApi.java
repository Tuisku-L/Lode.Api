package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;

import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.RouteManage;

import java.util.Map;
import java.util.UUID;

public class JsonApi extends PluginBase{
    public static JsonApi instance;

    public JsonApi(){
        JsonApi.instance = this;
    }

    @Override
    public void onLoad() {
        InitActions();
        RouteManage.RegisterRoute();
        ServerRunner.run(BaseHttpServer.class);
        getLogger().info("RESTful API Server run at: 8081 ");
    }

    private void InitActions(){
        RouteManage.allAction.add(UserAction.class);
        RouteManage.allAction.add(ServerAction.class);
        RouteManage.allAction.add(PluginAction.class);
    }
}