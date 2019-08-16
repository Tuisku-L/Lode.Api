package tech.v2c.minecraft.plugins.jsonApi;

import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.PluginBase;

import org.nanohttpd.util.ServerRunner;

import tech.v2c.minecraft.plugins.jsonApi.RESTful.actions.*;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseHttpServer;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.RouteManage;
import tech.v2c.minecraft.plugins.jsonApi.tools.YamlUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonApi extends PluginBase{
    public static JsonApi instance;
    public static int serverPort;
    public static String userName;
    public static String password;

    public JsonApi(){
        JsonApi.instance = this;
    }

    @Override
    public void onLoad() {
        InitPlugin();
        InitActions();
        RouteManage.RegisterRoute();
        (new Thread(new Runnable() {
            @Override
            public void run() {
                ServerRunner.run(BaseHttpServer.class);
            }
        })).start();
        getLogger().info("JsonAPI Server run at: " + this.serverPort);
    }

    @Override
    public void onDisable(){
        BaseHttpServer.instance.stop();
        getLogger().info("JsonAPI Server is shutdown.");
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
                this.serverPort = (int)(((Map<String, Object>)YamlUtils.GetValue(configFile, "Server")).get("Port"));
                this.userName = ((Map<String, String>)((Map<String, Object>)YamlUtils.GetValue(configFile, "Server")).get("Authentication")).get("UserName");
                this.password = ((Map<String, String>)((Map<String, Object>)YamlUtils.GetValue(configFile, "Server")).get("Authentication")).get("Password");
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