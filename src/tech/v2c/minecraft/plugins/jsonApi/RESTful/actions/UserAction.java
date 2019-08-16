package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.BanEntry;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.user.OnlineUserDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.user.UserPositionDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.results.JsonResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class UserAction extends BaseAction {
    public UserAction(){
        this.server = JsonApi.instance.getServer();
    }

    private Server server;

    @ApiRoute(Path="/api/User/GetUserByName")
    public JsonResult GetUserByName(JsonData data){
        String searchName = data.Data.get("Name").toString();
        Server server = JsonApi.instance.getServer();
        Map<UUID, Player> onlinePlayers = server.getOnlinePlayers();
        server.reload();
        // Player player = new OfflinePlayer(server, searchName).getPlayer();
        return new JsonResult(onlinePlayers);
    }

    @ApiRoute(Path="/api/User/GetOnlineList")
    public JsonResult GetOnlineUserList(){
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

    @ApiRoute(Path="/api/User/BanByName")
    public JsonResult BanUserByName(JsonData data){
        String userName = data.Data.get("name").toString();
        Object reason = data.Data.get("reason");
        Object startTime = data.Data.get("creationDate");
        Object endTime = data.Data.get("expirationDate");

        BanEntry be = new BanEntry(userName);
        be.setReason(reason == null ? "" : reason.toString());
        be.setCreationDate(startTime != null ? new Date(Long.parseLong(startTime.toString())) : new Date());
        if(endTime != null){
            be.setExpirationDate(new Date(Long.parseLong(endTime.toString())));
        }

        server.getNameBans().add(be);

        return new JsonResult(be);
    }

    @ApiRoute(Path="/api/User/BanByIp")
    public JsonResult BanUserByIp(JsonData data){
        String userIp = data.Data.get("ip").toString();
        Object reason = data.Data.get("reason");
        Object endTime = data.Data.get("expirationDate");
        Object startTime = data.Data.get("creationDate");

        BanEntry be = new BanEntry(userIp);
        be.setReason(reason == null ? "" : reason.toString());
        be.setCreationDate(startTime != null ? new Date(Long.parseLong(startTime.toString())) : new Date());
        if(endTime != null){
            be.setExpirationDate(new Date(Long.parseLong(endTime.toString())));
        }

        server.getIPBans().add(be);

        return new JsonResult(be);
    }

    @ApiRoute(Path="/api/User/GetNameBanList")
    public JsonResult GetNameBanList(){
        return new JsonResult(server.getNameBans().getEntires().values());
    }

    @ApiRoute(Path="/api/User/GetIpBanList")
    public JsonResult GetIpBanList(){
        return new JsonResult(server.getIPBans().getEntires().values());
    }

    @ApiRoute(Path="/api/User/GetWhiteList")
    public JsonResult GetWhiteList(){
        return new JsonResult(server.getWhitelist().getAll().keySet());
    }

    @ApiRoute(Path="/api/User/AddWhiteList")
    public JsonResult AddWhiteList(JsonData data){
        String userName = data.Data.get("name").toString();
        server.addWhitelist(userName);

        return GetWhiteList();
    }

    @ApiRoute(Path="/api/User/RemoveWhiteList")
    public JsonResult RemoveWhiteList(JsonData data){
        String userName = data.Data.get("name").toString();
        server.removeWhitelist(userName);

        return GetWhiteList();
    }

    @ApiRoute(Path="/api/User/GetOPList")
    public JsonResult GetOpList(){
        return new JsonResult(server.getOps().getAll().keySet());
    }

    @ApiRoute(Path="/api/User/AddOp")
    public JsonResult AddOp(JsonData data){
        String userName = data.Data.get("name").toString();
        server.addOp(userName);

        return GetOpList();
    }

    @ApiRoute(Path="/api/User/RemoveOp")
    public JsonResult RemoveOp(JsonData data){
        String userName = data.Data.get("name").toString();
        server.removeOp(userName);
        
        return GetOpList();
    }
}
