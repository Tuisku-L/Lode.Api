package tech.v2c.minecraft.plugins.lode.EventNotify.Console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import tech.v2c.minecraft.plugins.lode.EventNotify.global.LodeWebSocketServer;
import tech.v2c.minecraft.plugins.lode.tools.results.JsonResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ServerConsoleEvent extends AbstractAppender {
    public ServerConsoleEvent() {
        super("Lode", null, null);
    }

    @Override
    public void append(LogEvent logEvent) {
        HashMap<Object, Object> result = new HashMap<Object, Object>();
        result.put("type", "ServerConsoleEvent");
        result.put("message", "[" + new SimpleDateFormat("HH:mm:ss" ).format(new Date()) + "] " + "[" + logEvent.getLevel().name() + "] " + logEvent.getMessage().getFormattedMessage());

        LodeWebSocketServer.SendMsg(new JsonResult(result));
    }
}
