package tech.v2c.minecraft.plugins.jsonApi.tools;

import cn.nukkit.utils.LogLevel;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

public class LogUtils {
    public static void Log(LogLevel level, String msg){
        JsonApi.instance.getLogger().log(level, msg);
    }

    public static void Debug(String msg){
        if(JsonApi.instance.isDebugMode){
            JsonApi.instance.getLogger().debug(msg);
        }
    }

    public static void Info(String msg){
        JsonApi.instance.getLogger().info(msg);
    }
}