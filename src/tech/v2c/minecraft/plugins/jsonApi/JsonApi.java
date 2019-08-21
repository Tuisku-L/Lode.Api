package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.plugin.PluginBase;

import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.RouteManage;
import tech.v2c.minecraft.plugins.jsonApi.tools.YamlUtils;
import tech.v2c.minecraft.plugins.jsonApi.tools.configEntities.ServerConfig;

import java.io.File;
import java.io.IOException;

public class JsonApi extends PluginBase {
    public static JsonApi instance;
    public static ServerConfig config;

    public JsonApi() {
        JsonApi.instance = this;
    }

    @Override
    public void onEnable() {
        InitPlugin();
        InitActions();
        RouteManage.RegisterRoute();
        (new Thread(() -> ServerRunner.run(BaseHttpServer.class))).start();
        getLogger().info("JsonAPI Http Server running at: " + this.config.getHttpPort());
    }

    @Override
    public void onDisable() {
        BaseHttpServer.instance.stop();
        getLogger().info("JsonAPI Server is shutdown.");
    }

    private void InitActions() {
        RouteManage.allAction.add(UserAction.class);
        RouteManage.allAction.add(ServerAction.class);
        RouteManage.allAction.add(PluginAction.class);
        RouteManage.allAction.add(ItemAction.class);
    }

    private void InitPlugin() {
        String configPath = getServer().getPluginPath() + "/JsonApi";
        File dir = new File(configPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                File configFile = new File(configPath + "/config.yml");
                try {
                    if (configFile.createNewFile()) {
                        YamlUtils.SetValue(configFile, "Server", DefaultConfig());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.config = GetConfig();
    }

    private ServerConfig DefaultConfig() {
        ServerConfig config = new ServerConfig();
        ServerConfig.Authentication auth = new ServerConfig.Authentication();
        auth.setUserName("root");
        auth.setPassword("password");
        config.setAuthentication(auth);
        config.setIP("0.0.0.0");
        config.setHttpPort(this.getServer().getPort() + 1);
        config.setWsPort(this.getServer().getPort() + 2);

        return config;
    }

    private ServerConfig GetConfig(){
        String configPath = getServer().getPluginPath() + "/JsonApi";
        File configFile = new File(configPath + "/config.yml");
        try {
            return YamlUtils.GetValue(configFile, "Server", ServerConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}