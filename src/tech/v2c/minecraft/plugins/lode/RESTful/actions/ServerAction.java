package tech.v2c.minecraft.plugins.lode.RESTful.actions;

import cn.nukkit.Server;

import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.Config;
import tech.v2c.minecraft.plugins.lode.Lode;
import tech.v2c.minecraft.plugins.lode.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.lode.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.lode.RESTful.global.entities.server.ServerDTO;
import tech.v2c.minecraft.plugins.lode.RESTful.global.entities.server.ServerStatusDTO;
import tech.v2c.minecraft.plugins.lode.tools.results.JsonResult;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ServerAction extends BaseAction {
    @ApiRoute(Path = "/api/Server/GetServerInfo")
    public JsonResult GetServerInfo() {
        Server server = Lode.instance.getServer();

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
        serverInfo.setServerType(server.getClass().getName());

        return new JsonResult(serverInfo);
    }

    @ApiRoute(Path = "/api/Server/ExecuteCommand")
    public JsonResult ExecuteCommand(Map data) {
        String cmd = data.get("command").toString();
        server.getScheduler().scheduleTask(Lode.instance, new Runnable() {
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
    public JsonResult SetMaxPlayer(Map data) {
        int maxPlayer = (int) Double.parseDouble(data.get("maxPlayer").toString());
        server.setMaxPlayers(maxPlayer);

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/Server/SendBroadcastMessage")
    public JsonResult SendBroadcastMessage(Map data) {
        String message = data.get("message").toString();

        return new JsonResult(server.broadcastMessage(message));
    }

    @ApiRoute(Path = "/api/Server/SetServerProps")
    public JsonResult SetServerProps(Map data) {
        String key = data.get("key").toString();
        String value = data.get("value").toString();

        Config conf = server.getProperties();
        conf.set(key, value);

        return new JsonResult(conf.save());
    }

    @ApiRoute(Path = "/api/Server/SetWhitelistState")
    public JsonResult SetWhitelistState(Map data) {
        boolean state = Boolean.parseBoolean(data.get("state").toString());
        Config conf = server.getProperties();
        conf.set("white-list", state ? "on" : "off");

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/Server/SetGameMode")
    public JsonResult SetGameMode(Map data) {
        int gameMode = (int) Double.parseDouble(data.get("gameMode").toString());
        Config conf = server.getProperties();
        conf.set("gamemode", gameMode);
        return new JsonResult();
    }

    @ApiRoute(Path = "/api/Server/GetStatus")
    public JsonResult GetStatus(){
        ServerStatusDTO state = new ServerStatusDTO();

        state.setOnlinePlayer(server.getOnlinePlayers().size());
        state.setMaxPlayer(server.getMaxPlayers());

        return new JsonResult(state);
    }
}
