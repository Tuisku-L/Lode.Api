package tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils;

import cn.nukkit.Player;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

import java.util.UUID;

public class UserUtils {
    public static Player GetPlayerByName(String name){
        Player player = JsonApi.instance.getServer().getPlayer(name);

        return player;
    }

    public static Player GetPlayerByUuid(UUID uuid){
        Player player = JsonApi.instance.getServer().getPlayer(uuid).get();

        return player;
    }
}
