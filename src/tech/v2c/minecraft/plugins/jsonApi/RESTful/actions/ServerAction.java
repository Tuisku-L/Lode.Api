package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Server;

import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.server.ServerDTO;
import tech.v2c.minecraft.plugins.jsonApi.tools.results.JsonResult;

import java.util.Timer;
import java.util.TimerTask;

public class ServerAction extends BaseAction {
    @ApiRoute(Path = "/api/Server/GetServerInfo")
    public JsonResult GetServerInfo() {
        Server server = JsonApi.instance.getServer();

        ServerDTO serverInfo = new ServerDTO();
        serverInfo.setPort(server.getPort());
        ;
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
        serverInfo.setAutoSave(server.getAutoSave());
        serverInfo.setHasWhiteList(server.hasWhitelist());

        return new JsonResult(serverInfo);
    }

    @ApiRoute(Path = "/api/Server/ExecuteCommand")
    public JsonResult ExecuteCommand(JsonData data) {
        String cmd = data.Data.get("command").toString();
        server.getScheduler().scheduleTask(JsonApi.instance, new Runnable() {
            @Override
            public void run() {
                server.dispatchCommand(new ConsoleCommandSender(), cmd);
            }
        });
        return new JsonResult();
    }

    @ApiRoute(Path = "/api/Server/ReloadServer")
    public JsonResult ReloadServer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                server.reload();
            }
        }, 5000);

        return new JsonResult(null, 200, "Server will have reload after 5 seconds.");
    }

    @ApiRoute(Path = "/api/Server/SetMaxPlayer")
    public JsonResult SetMaxPlayer(JsonData data) {
        int maxPlayer = (int) Double.parseDouble(data.Data.get("maxPlayer").toString());
        server.setMaxPlayers(maxPlayer);

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/Server/SendBroadcastMessage")
    public JsonResult SendBroadcastMessage(JsonData data) {
        String message = data.Data.get("message").toString();

        return new JsonResult(server.broadcastMessage(message));
    }

    @ApiRoute(Path = "/api/Server/SetServerProps")
    public JsonResult SetServerProps(JsonData data) {
        String key = data.Data.get("key").toString();
        String value = data.Data.get("value").toString();

        Config conf = server.getProperties();
        conf.set(key, value);

        return new JsonResult(conf.save());
    }

    @ApiRoute(Path = "/api/Server/SetWhitelistState")
    public JsonResult SetWhitelistState(JsonData data) {
        boolean state = Boolean.parseBoolean(data.Data.get("state").toString());
        Config conf = server.getProperties();
        conf.set("white-list", state ? "on" : "off");

        return new JsonResult();
    }
}
