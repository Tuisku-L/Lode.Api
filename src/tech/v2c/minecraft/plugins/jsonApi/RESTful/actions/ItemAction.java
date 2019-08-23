package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.item.ItemDTO;
import tech.v2c.minecraft.plugins.jsonApi.tools.results.JsonResult;
import tech.v2c.minecraft.plugins.jsonApi.tools.gameUtils.UserUtils;

import java.util.ArrayList;

public class ItemAction extends BaseAction {
    @ApiRoute(Path = "/api/Item/GetList")
    public JsonResult GetItemList() {
        ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
        for (int i = 0; i < Item.list.length; i++) {
            Item item = Item.get(i);
            if(item.getName().toLowerCase().contains("unknown")){
                continue;
            }
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setHasMeta(item.hasMeta());
            list.add(itemDTO);
        }

        return new JsonResult(list);
    }

    @ApiRoute(Path = "/api/Item/GetItemInfo")
    public JsonResult GetItemInfo(JsonData data){
        int itemId = (int) Double.parseDouble(data.Data.get("item").toString());

        Item item = Item.get(itemId);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setHasMeta(item.hasMeta());

        return new JsonResult(itemDTO);
    }

    @ApiRoute(Path = "/api/Item/SendItemToUser")
    public JsonResult SendItem(JsonData data) {
        String userName = data.Data.get("name").toString();
        int itemId = (int) Double.parseDouble(data.Data.get("item").toString());
        Object count = data.Data.get("count");
        Object meta = data.Data.get("meta");
        Object msg = data.Data.get("message");

        Player player = UserUtils.GetPlayerByName(userName);
        if (player == null) return new JsonResult(null, 404, "Error: user not found.");

        if (player.getInventory().isFull()) {
            return new JsonResult(null, 409, "Error: Player's Inventory is full.");
        } else {
            int firstEmpty = player.getInventory().firstEmpty(null);
            Item item = Item.get(itemId, meta == null ? 100 : (int) Double.parseDouble(meta.toString()), count == null ? 1 : (int) Double.parseDouble(count.toString()));
            if(item.getName().toLowerCase().contains("unknown")){
                return new JsonResult(null, 404, "Error: item not found.");
            }
            boolean result = player.getInventory().setItem(firstEmpty, item);
            if(player.getInventory().setItem(firstEmpty, item)){
                if(msg != null){
                    player.sendMessage(msg.toString());
                }
                return new JsonResult(result);
            }else{
                return new JsonResult(null, 400, "Error: can not send item to player.");
            }
        }
    }
}
