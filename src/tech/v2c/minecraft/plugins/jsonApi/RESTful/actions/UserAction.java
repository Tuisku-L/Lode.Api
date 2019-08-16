package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.results.JsonResult;

import java.util.Map;
import java.util.UUID;

public class UserAction extends BaseAction {
    @ApiRoute(Path="/api/User/GetUserByName")
    public JsonResult GetUserByName(JsonData data){
        String searchName = data.Data.get("Name").toString();
        Server server = JsonApi.instance.getServer();
        Map<UUID, Player> onlinePlayers = server.getOnlinePlayers();
        server.reload();
        // Player player = new OfflinePlayer(server, searchName).getPlayer();
        return new JsonResult(onlinePlayers);
    }
}
