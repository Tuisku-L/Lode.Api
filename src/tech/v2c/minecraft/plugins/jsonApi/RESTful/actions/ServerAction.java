package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Server;

import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.server.ServerDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.results.JsonResult;

public class ServerAction extends BaseAction {
    @ApiRoute(Path="/api/Server/GetServerInfo")
    public JsonResult GetServerInfo(){
        Server server = JsonApi.instance.getServer();

        ServerDTO serverInfo = new ServerDTO();
        serverInfo.setPort(server.getPort());;
        serverInfo.setVersion(server.getVersion());
        serverInfo.setOnlinePlayerCount(server.getOnlinePlayers().size());
        serverInfo.setIp(server.getIp());
        serverInfo.setMaxPlayerCount(server.getMaxPlayers());
        serverInfo.setMotd(server.getMotd());
        serverInfo.setSubMotd(server.getSubMotd());
        serverInfo.setSubMotd(server.getSubMotd());
        serverInfo.setNukkitVersion(server.getNukkitVersion());
        serverInfo.setApiVersion(server.getApiVersion());
        serverInfo.setGameMode(server.getGamemode());
        serverInfo.setDifficulty(server.getDifficulty());
        serverInfo.setPluginCount(server.getPluginManager().getPlugins().size());

        return new JsonResult(serverInfo);
    }
}
