package tech.v2c.minecraft.plugins.lode.tools;

import cn.nukkit.utils.LogLevel;
import tech.v2c.minecraft.plugins.lode.Lode;

public class LogUtils {
    public static void Log(LogLevel level, String msg){
        Lode.instance.getLogger().log(level, msg);
    }

    public static void Debug(String msg){
        if(Lode.instance.isDebugMode){
            Lode.instance.getLogger().debug(msg);
        }
    }

    public static void Info(String msg){
        Lode.instance.getLogger().info(msg);
    }
}