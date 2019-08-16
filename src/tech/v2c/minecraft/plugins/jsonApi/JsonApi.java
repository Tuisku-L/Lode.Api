package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;

import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.RouteManage;
import tech.v2c.minecraft.plugins.jsonApi.tools.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonApi extends PluginBase{
    public static JsonApi instance;
    public static int serverPort;

    public JsonApi(){
        JsonApi.instance = this;
    }

    @Override
    public void onLoad() {
        InitPlugin();
        InitActions();
        RouteManage.RegisterRoute();
        ServerRunner.run(BaseHttpServer.class);
        getLogger().info("JsonAPI Server run at: " + this.serverPort);
    }

    private void InitActions(){
        RouteManage.allAction.add(UserAction.class);
        RouteManage.allAction.add(ServerAction.class);
        RouteManage.allAction.add(PluginAction.class);
    }

    private void InitPlugin(){
        String configPath = getServer().getPluginPath() + "/JsonApi";
        File dir = new File(configPath);
        if(!dir.exists()){
            if(dir.mkdir()){
                File configFile = new File(configPath + "/config.yml");
                try {
                    if(configFile.createNewFile()){
                        YamlUtils.SetValue(configFile, "Server", GetDefaultConfig());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            File configFile = new File(configPath + "/config.yml");
            try {
                int port = (int)(((HashMap<String, Object>)YamlUtils.GetValue(configFile, "Server")).get("Port"));
                this.serverPort = port;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map GetDefaultConfig(){
        Map initConfig = new HashMap<String, Object>(){{
            this.put("IP", "0.0.0.0");
            this.put("Port", 19133);
            this.put("Authentication", new HashMap<String, Object>(){{
                this.put("UserName", "root");
                this.put("Password", "password");
            }});
        }};

        this.serverPort = 19133;

        return initConfig;
    }
}