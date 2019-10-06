package tech.v2c.minecraft.plugins.lode.tools.results;

public class JsonResult {
    private int code;
    private String message;
    private Object returnObject;

    public JsonResult(Object returnObject, int code,String message){
        this.code = code;
        this.message = message;
        this.returnObject = returnObject;
    }

    public JsonResult( int code, String message){
        this.code = code;
        this.message = message;
        this.returnObject = null;
    }

    public JsonResult(Object returnObject){
        this.code = 200;
        this.message = "";
        this.returnObject = returnObject;
    }

    public JsonResult(){
        this.code = 200;
        this.message = "";
        this.returnObject = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }
}
