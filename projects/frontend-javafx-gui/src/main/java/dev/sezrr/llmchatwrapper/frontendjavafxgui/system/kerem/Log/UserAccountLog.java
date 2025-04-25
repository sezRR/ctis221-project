package Log;

import java.io.*;

public class UserAccountLog extends Log<UserEvent> {
    protected String prefix = "USER ACCOUNT LOG >> ";
    PrintWriter output = null;
    File file = new File("log.txt");


    void log(String message) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        PrintWriter output = new PrintWriter(fw);
        output.println(prefix + message);
        fw.close();
        output.close();
    }



}
