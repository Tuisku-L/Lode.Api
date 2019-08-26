package tech.v2c.minecraft.plugins.jsonApi.EventNotify.Console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import tech.v2c.minecraft.plugins.jsonApi.EventNotify.global.JsonApiWebSocketServer;
import tech.v2c.minecraft.plugins.jsonApi.tools.results.JsonResult;

import java.util.Date;
import java.util.HashMap;

public class ServerConsoleEvent extends AbstractAppender {
    public ServerConsoleEvent() {
        super("JSONAPI", null, null);
    }

    @Override
    public void append(LogEvent logEvent) {
        HashMap<Object, Object> result = new HashMap<Object, Object>();
        result.put("type", "ServerConsoleEvent");
        result.put("message", "[" + new Date().toString() + "] " + "[" + logEvent.getLevel().name() + "] " + logEvent.getMessage().getFormattedMessage());

        JsonApiWebSocketServer.SendMsg(new JsonResult(result));
    }
}
