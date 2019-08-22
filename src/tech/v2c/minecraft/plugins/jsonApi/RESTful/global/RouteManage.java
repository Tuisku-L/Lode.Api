package tech.v2c.minecraft.plugins.jsonApi.RESTful.global;

import com.google.gson.Gson;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.annotations.ApiRoute;
import tech.v2c.minecraft.plugins.jsonApi.RESTful.global.entities.JsonData;
import tech.v2c.minecraft.plugins.jsonApi.tools.results.JsonResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteManage {
    public static HashMap<String, Method> actionRouteMap = new HashMap();
    public static ArrayList<Class> allAction = new ArrayList<Class> ();
    private ArrayList<Method> allMethods = new ArrayList<Method>();

    // 注册所有路由
    public static void RegisterRoute(){
        for (Class item : allAction) {
            Method[] methods = item.getDeclaredMethods();
            for (Method method: methods) {
                ApiRoute an = method.getAnnotation(ApiRoute.class);
                actionRouteMap.put(an.Path().toLowerCase(), method);
            }
        }
    }

    // 调用对应的方法
    public static String TouchAction(String url, JsonData jsonData){
        Method action = TryGetAction(url);
        if(action == null) return "404";
        JsonResult result = null;
        try {
            if(action.getParameterCount() > 0){
                result = (JsonResult)action.invoke(action.getDeclaringClass().newInstance(), jsonData);
            }else{
                result = (JsonResult)action.invoke(action.getDeclaringClass().newInstance());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(result);
    }

    // 尝试获取对应的方法
    public static Method TryGetAction(String url){
        if(actionRouteMap.containsKey(url)){
            return actionRouteMap.get(url);
        }
        return null;
    }
}