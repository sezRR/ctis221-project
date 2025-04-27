package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.log;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin.AdminEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminModelLog extends Log<AdminEvent> {
    protected String prefix = "ADMIN ACCOUNT LOG >> ";
    File file = new File("admin-log.txt");

    @Override
    public void log(String message) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        PrintWriter output = new PrintWriter(fw);
        output.println(prefix + message);
        fw.close();
        output.close();
    }
}
