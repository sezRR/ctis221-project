package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.log;

import java.io.IOException;

public class SystemLog extends Log<SystemLog> {
    protected String prefix = "SYSTEM LOG >> ";
    
    @Override
    public void log(String message) throws IOException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        System.out.println(prefix + message);
    }
}
