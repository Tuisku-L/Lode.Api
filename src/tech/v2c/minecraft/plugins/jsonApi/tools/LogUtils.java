package tech.v2c.minecraft.plugins.jsonApi.tools;

import cn.nukkit.Server;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.MainLogger;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

public class LogUtils {
    private static MainLogger logger;

    public LogUtils(){
        this.logger = JsonApi.instance.getServer().getLogger();
    }

    public static void Log(LogLevel level, String msg){
        logger.log(level, msg);
    }

    public static void Debug(String msg){
        if(JsonApi.instance.isDebugMode){
            logger.debug(msg);
        }
    }

    public static void Info(String msg){
        logger.info(msg);
    }
}