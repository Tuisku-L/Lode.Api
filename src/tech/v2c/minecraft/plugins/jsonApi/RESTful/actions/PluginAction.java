package tech.v2c.minecraft.plugins.jsonApi.RESTful.actions;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginManager;
import tech.v2c.minecraft.plugins.jsonApi.JsonApi;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.BaseAction;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.server.PluginDTO;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.results.JsonResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static cn.nukkit.utils.Utils.copyFile;

public class PluginAction extends BaseAction {
    private final PluginManager pluginManager;

    public PluginAction(){
        pluginManager = server.getPluginManager();
    }

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

    @ApiRoute(Path="/api/Plugin/GetPluginInfo")
    public JsonResult GetPluginInfo(JsonData data){
        Plugin plugin = pluginManager.getPlugin(data.Data.get("name").toString());

        if(plugin == null) return new JsonResult(null, 404, "Error: plugin not found.");

        PluginDTO plg = new PluginDTO();
        plg.setName(plugin.getName());
        plg.setVersion(plugin.getDescription().getVersion());
        plg.setWebsite(plugin.getDescription().getWebsite());
        plg.setEnabled(plugin.isEnabled());
        plg.setDisabled(plugin.isDisabled());
        plg.setAuthors(plugin.getDescription().getAuthors());
        plg.setDescription(plugin.getDescription().getDescription());
        
        return new JsonResult(plg);
    }

    // 关闭指定插件
    @ApiRoute(Path="/api/Plugin/Disable")
    public JsonResult DisablePluginByName(JsonData data){
        Plugin plugin = pluginManager.getPlugin(data.Data.get("name").toString());

        if(plugin == null) return new JsonResult(null, 404, "Error: plugin not found.");

        if(!pluginManager.isPluginEnabled(plugin)){
            return new JsonResult(null, 401, "Plugin is disable alright.");
        }

        pluginManager.disablePlugin(plugin);
        return GetPluginList();
    }

    // 开启指定插件
    @ApiRoute(Path="/api/Plugin/Enable")
    public JsonResult EnablePluginByName(JsonData data){
        Plugin plugin = pluginManager.getPlugin(data.Data.get("name").toString());

        if(plugin == null) return new JsonResult(null, 404, "Error: plugin not found.");

        pluginManager.enablePlugin(plugin);

        return GetPluginList();
    }

    // 关闭所有插件
    @ApiRoute(Path="/api/Plugin/Disable/All")
    public JsonResult DisableAllPlugins(){
        pluginManager.disablePlugins();

        return GetPluginList();
    }

    // 安装插件
    @ApiRoute(Path="/api/Plugin/Install")
    public JsonResult InstallPlugin(JsonData data){
        Map<String, File> allFile = ( Map<String, File>)data.Data.get("files");
        String pluginPath = server.getPluginPath();

        for (Map.Entry<String, File> plg : allFile.entrySet()) {
            final String fileName = plg.getKey();
            final File file = plg.getValue();
            final String copyPath = pluginPath + fileName;
            try {
                copyFile(file, new File(copyPath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Plugin uploadPlg = pluginManager.loadPlugin(copyPath);
            pluginManager.enablePlugin(uploadPlg);
        }

        return GetPluginList();
    }
}
