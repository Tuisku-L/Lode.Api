package tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils;

import cn.nukkit.event.Listener;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

public class EventUtils {
    public static void RegisterEvent(Listener listener){
        JsonApi.instance.getServer().getPluginManager().registerEvents(listener, JsonApi.instance);
    }
}
