package tech.v2c.minecraft.plugins.lode.tools.gameUtils;

import cn.nukkit.event.Listener;
import tech.v2c.minecraft.plugins.lode.Lode;

public class EventUtils {
    public static void RegisterEvent(Listener listener){
        Lode.instance.getServer().getPluginManager().registerEvents(listener, Lode.instance);
    }
}
