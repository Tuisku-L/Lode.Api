package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.item.Item;
import cn.nukkit.permission.BanEntry;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.user.OnlineUserDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.user.UserPositionDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.results.JsonResult;
import tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils.UserUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class UserAction extends BaseAction {
    @ApiRoute(Path = "/api/User/GetUserByName")
    public JsonResult GetUserByName(JsonData data) {
        String searchName = data.Data.get("name").toString();
        Player player = UserUtils.GetPlayerByName(searchName);
        return new JsonResult(player);
    }

    @ApiRoute(Path = "/api/User/GetOnlineList")
    public JsonResult GetOnlineUserList() {
        Map<UUID, Player> users = server.getOnlinePlayers();
        ArrayList<OnlineUserDTO> userList = new ArrayList<OnlineUserDTO>();

        for (Map.Entry<UUID, Player> user : users.entrySet()) {
            OnlineUserDTO onlineUser = new OnlineUserDTO();
            onlineUser.setName(user.getValue().getName());
            onlineUser.setDisplayName(user.getValue().getDisplayName());
            onlineUser.setId(user.getValue().getId());
            onlineUser.setUid(user.getKey());
            onlineUser.setGamemode(user.getValue().getGamemode());
            onlineUser.setHeight(user.getValue().getHeight());
            onlineUser.setHealth(user.getValue().getHealth());
            onlineUser.setMaxHealth(user.getValue().getMaxHealth());
            onlineUser.setPing(user.getValue().getPing());

            UserPositionDTO up = new UserPositionDTO();
            up.setX(user.getValue().getPosition().getX());
            up.setY(user.getValue().getPosition().getY());
            up.setZ(user.getValue().getPosition().getZ());

            onlineUser.setPosition(up);

            userList.add(onlineUser);
        }
        return new JsonResult(userList);
    }

    @ApiRoute(Path = "/api/User/BanByName")
    public JsonResult BanUserByName(JsonData data) {
        String userName = data.Data.get("name").toString();
        Object reason = data.Data.get("reason");
        Object startTime = data.Data.get("creationDate");
        Object endTime = data.Data.get("expirationDate");

        BanEntry be = new BanEntry(userName);
        be.setReason(reason == null ? "" : reason.toString());
        be.setCreationDate(startTime != null ? new Date(Long.parseLong(startTime.toString())) : new Date());
        if (endTime != null) {
            be.setExpirationDate(new Date(Long.parseLong(endTime.toString())));
        }

        server.getNameBans().add(be);

        return new JsonResult(be);
    }

    @ApiRoute(Path = "/api/User/BanByIp")
    public JsonResult BanUserByIp(JsonData data) {
        String userIp = data.Data.get("ip").toString();
        Object reason = data.Data.get("reason");
        Object endTime = data.Data.get("expirationDate");
        Object startTime = data.Data.get("creationDate");

        BanEntry be = new BanEntry(userIp);
        be.setReason(reason == null ? "" : reason.toString());
        be.setCreationDate(startTime != null ? new Date(Long.parseLong(startTime.toString())) : new Date());
        if (endTime != null) {
            be.setExpirationDate(new Date(Long.parseLong(endTime.toString())));
        }

        server.getIPBans().add(be);

        return new JsonResult(be);
    }

    @ApiRoute(Path = "/api/User/GetNameBanList")
    public JsonResult GetNameBanList() {
        return new JsonResult(server.getNameBans().getEntires().values());
    }

    @ApiRoute(Path = "/api/User/GetIpBanList")
    public JsonResult GetIpBanList() {
        return new JsonResult(server.getIPBans().getEntires().values());
    }

    @ApiRoute(Path = "/api/User/GetWhiteList")
    public JsonResult GetWhiteList() {
        return new JsonResult(server.getWhitelist().getAll().keySet());
    }

    @ApiRoute(Path = "/api/User/AddWhiteList")
    public JsonResult AddWhiteList(JsonData data) {
        String userName = data.Data.get("name").toString();
        server.addWhitelist(userName);

        return GetWhiteList();
    }

    @ApiRoute(Path = "/api/User/RemoveWhiteList")
    public JsonResult RemoveWhiteList(JsonData data) {
        String userName = data.Data.get("name").toString();
        server.removeWhitelist(userName);

        return GetWhiteList();
    }

    @ApiRoute(Path = "/api/User/GetOPList")
    public JsonResult GetOpList() {
        return new JsonResult(server.getOps().getAll().keySet());
    }

    @ApiRoute(Path = "/api/User/AddOp")
    public JsonResult AddOp(JsonData data) {
        String userName = data.Data.get("name").toString();
        server.addOp(userName);

        return GetOpList();
    }

    @ApiRoute(Path = "/api/User/RemoveOp")
    public JsonResult RemoveOp(JsonData data) {
        String userName = data.Data.get("name").toString();
        server.removeOp(userName);

        return GetOpList();
    }

    @ApiRoute(Path = "/api/User/SetGameMode")
    public JsonResult SetGameMode(JsonData data) {
        String userName = data.Data.get("name").toString();
        int gameMode = (int) Double.parseDouble(data.Data.get("gameMode").toString());
        Player user = UserUtils.GetPlayerByName(userName);
        if (user == null) return new JsonResult(null, 404, "Error: user not found.");

        return new JsonResult(user.setGamemode(gameMode));
    }

    @ApiRoute(Path = "/api/User/SendChat")
    public JsonResult SendChat(JsonData data) {
        String userName = data.Data.get("name").toString();
        String message = data.Data.get("message").toString();
        Object source = data.Data.get("source");

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.sendChat(source == null ? "" : source.toString(), message);

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/SendMessage")
    public JsonResult SendMessage(JsonData data) {
        String userName = data.Data.get("name").toString();
        String message = data.Data.get("message").toString();

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.sendMessage(message);

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/SendExperience")
    public JsonResult SendExperience(JsonData data) {
        String userName = data.Data.get("name").toString();
        int expType = (int) Double.parseDouble(data.Data.get("type").toString());
        int value = (int) Double.parseDouble(data.Data.get("value").toString());

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        if (expType == 0) {
            player.sendExperience(value);
        } else {
            player.sendExperienceLevel(value);
        }

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/SetPlayerFire")
    public JsonResult SetPlayerFire(JsonData data) {
        String userName = data.Data.get("name").toString();
        int time = (int) Double.parseDouble(data.Data.get("time").toString());

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.setOnFire(time);
        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/KillPlayer")
    public JsonResult KillPlayer(JsonData data) {
        String userName = data.Data.get("name").toString();

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.kill();
        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/KickPlayer")
    public JsonResult KickPlayer(JsonData data) {
        String userName = data.Data.get("name").toString();
        boolean isKickByAdmin = (boolean) data.Data.get("isKickByAdmin");
        Object reason = data.Data.get("reason");

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.kick(reason == null ? PlayerKickEvent.Reason.UNKNOWN.toString() : reason.toString(), isKickByAdmin);

        return new JsonResult();
    }

    @ApiRoute(Path = "/api/User/ClearPlayerInventory")
    public JsonResult ClearPlayerInventory(JsonData data){
        String userName = data.Data.get("name").toString();

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        player.getInventory().clearAll();

        return new JsonResult();
    }
}
