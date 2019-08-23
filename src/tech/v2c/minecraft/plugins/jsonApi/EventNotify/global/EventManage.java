package tech.v2c.minecraft.plugins.jsonApi.EventNotify.global;

import cn.nukkit.event.Listener;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils.EventUtils;

import java.util.HashMap;
import java.util.List;

public class EventManage {
    public static HashMap<String, Listener> allEvent = new HashMap<String, Listener> ();

    public static void RegisterEventListener(){
        List events = JsonApi.instance.getConfig().getSection("EventListener").getList("EventList");
        allEvent.entrySet().forEach(val -> {
            if(events.contains(val.getKey())){
                EventUtils.RegisterEvent(val.getValue());
            }
        });
    }
}
