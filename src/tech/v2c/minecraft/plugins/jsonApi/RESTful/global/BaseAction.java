package tech.v2c.minecraft.plugins.jsonApi.RESTful.global;

import cn.nukkit.Server;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;

public class BaseAction {
    public final Server server;

    public BaseAction(){
        this.server = JsonApi.instance.getServer();
    }
}
