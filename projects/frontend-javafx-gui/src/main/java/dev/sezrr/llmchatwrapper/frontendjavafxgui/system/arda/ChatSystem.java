package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.*;

public class ChatSystem {
    private static Map<UUID, Chat> chats = new HashMap<>();

    public static Chat getChat(UUID uuid) {
        return chats.get(uuid);
    }
    public static Chat searchChat(UUID uuid) {
        for(Chat chat : chats.values()) {
            if(chat.getChatId().equals(uuid)) {
                return chat;
            }
        }
        return null;
    }
    public static boolean deleteChat(UUID uuid) {
        Chat chat=searchChat(uuid);
        if(chat!=null) {
            chats.remove(uuid);
            return true;
        }
        return false;
    }

    public static boolean addChat(Chat chat) {
        if(searchChat(chat.getChatId()) == null) {
            chats.put(chat.getChatId(), chat);
            return true;
        }
        return false;
    }

    public static List<Chat> getChats() {
        return new ArrayList<>(chats.values());
    }

    public boolean sendMessage(UUID chatId, ChatMessage message) throws Exception {
        Chat chat = chats.get(chatId);
        if (chat != null) {
            chat.addChatMessage(message); // assuming Chat has addMessage()
            return true;
        }
        return false;
    }

    public static String displayChats()
    {
        String output = "";
        for(Chat chat : chats.values()) {
            output += chat.toString() + "\n";
        }
        return output;
    }

    public static boolean downloadChat(UUID uuid) throws IOException {
        PrintWriter output=null;
        File file = new File("chats/"+uuid+".txt");
        if(file.exists()) {
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);
            output.println(displayChats());
            fw.close();
            pw.close();
            return true;


        }
        throw new ExportException("No file found");
    }
}
