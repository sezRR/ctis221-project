package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.log;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.user.UserEvent;

import java.io.*;

public class UserAccountLog extends Log<UserEvent> {
    protected String prefix = "USER ACCOUNT LOG >> ";
    PrintWriter output = null;
    File file = new File("log.txt");

    @Override
    public void log(String message) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        PrintWriter output = new PrintWriter(fw);
        output.println(prefix + message);
        fw.close();
        output.close();
    }
}
