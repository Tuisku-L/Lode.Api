package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginManager;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.entities.server.PluginDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.utils.results.JsonResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PluginAction extends BaseAction {
    public PluginAction(){
        this.server = JsonApi.instance.getServer();
        pluginManager = server.getPluginManager();
    }

    private Server server;
    private PluginManager pluginManager;

    // 获取插件列表
    @ApiRoute(Path="/api/Plugin/GetList")
    public JsonResult GetPluginList(){
        ArrayList<PluginDTO> pluginList = new ArrayList<PluginDTO>();

        Map<String, Plugin> plugins = pluginManager.getPlugins();
        for(Iterator<Map.Entry<String, Plugin>> it = plugins.entrySet().iterator(); it.hasNext();){
            Map.Entry<String, Plugin> itt = it.next();
            PluginDTO plg = new PluginDTO();
            plg.setName(itt.getKey());
            plg.setVersion(itt.getValue().getDescription().getVersion());
            plg.setWebsite(itt.getValue().getDescription().getWebsite());
            plg.setEnabled(itt.getValue().isEnabled());
            plg.setDisabled(itt.getValue().isDisabled());
            plg.setAuthors(itt.getValue().getDescription().getAuthors());
            plg.setDescription(itt.getValue().getDescription().getDescription());

            pluginList.add(plg);
        }

        return new JsonResult(pluginList);
    }

    // 关闭指定插件
    @ApiRoute(Path="/api/Plugin/Disable")
    public JsonResult DisablePluginByName(JsonData data){
        Plugin plugin = pluginManager.getPlugin(data.Data.get("name").toString());
        if(!pluginManager.isPluginEnabled(plugin)){
            return new JsonResult(null, 401, "Plugin is disable alright.");
        }

        pluginManager.disablePlugin(plugin);
        return new JsonResult();
    }

    // 开启指定插件
    @ApiRoute(Path="/api/Plugin/Enable")
    public JsonResult EnablePluginByName(JsonData data){
        Plugin plugin = pluginManager.getPlugin(data.Data.get("name").toString());
        pluginManager.enablePlugin(plugin);

        return new JsonResult();
    }

    // 关闭所有插件
    @ApiRoute(Path="/api/Plugin/Disable/All")
    public JsonResult DisableAllPlugins(){
        pluginManager.disablePlugins();

        return new JsonResult();
    }

    @ApiRoute(Path="/api/Plugin/Install")
    public JsonResult InstallPlugin(){
        return null;
    }
}
