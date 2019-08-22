package tech.v2c.minecraft.plugins.jsonApi.EventNotify.global;

import cn.nukkit.event.Listener;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.Events.CommandEvent;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

import java.util.ArrayList;

public class EventManage {
    public static ArrayList<Listener> allEvent = new ArrayList<Listener> ();

    public static void RegisterEventListener(){
        allEvent.forEach(event -> {
            JsonApi.instance.getServer().getPluginManager().registerEvents(event, JsonApi.instance);
        });
    }
}
