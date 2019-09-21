package tech.v2c.minecraft.plugins.lode.EventNotify.global;

import cn.nukkit.event.Listener;
import tech.v2c.minecraft.plugins.lode.Lode;
import tech.v2c.minecraft.plugins.lode.tools.LogUtils;
import tech.v2c.minecraft.plugins.lode.tools.gameUtils.EventUtils;

import java.util.HashMap;
import java.util.List;

public class EventManage {
    public static HashMap<String, Listener> allEvent = new HashMap<String, Listener>();

    public static void RegisterEventListener() {
        List events = Lode.instance.getConfig().getSection("EventListener").getList("EventList");
        if (events != null) {
            allEvent.entrySet().forEach(val -> {
                if (events.contains(val.getKey())) {
                    EventUtils.RegisterEvent(val.getValue());
                }
            });
        } else {
            LogUtils.Info("EventList is empty!");
        }
    }
}
