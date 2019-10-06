package tech.v2c.minecraft.plugins.lode.RESTful.global;

import cn.nukkit.Server;
import tech.v2c.minecraft.plugins.lode.Lode;

public class BaseAction {
    public final Server server;

    public BaseAction(){
        this.server = Lode.instance.getServer();
    }
}
