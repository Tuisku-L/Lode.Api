package tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

import java.util.Map;
import java.util.UUID;

public class UserUtils {
    public static Player GetPlayerByName(String name){
        Map<UUID, Player> onlinePlayers = JsonApi.instance.getServer().getOnlinePlayers();
        Player player = null;
        for (Map.Entry<UUID, Player> item : onlinePlayers.entrySet()) {
            if(item.getValue().getName().equalsIgnoreCase(name)){
                player = item.getValue();
                break;
            }
        }

        return player;
    }
}
