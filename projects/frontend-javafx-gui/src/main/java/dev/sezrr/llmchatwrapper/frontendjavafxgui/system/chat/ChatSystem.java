package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiConfig;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.StandardRequestStrategy;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ExportException;
import java.util.*;

public class ChatSystem {
    private static Map<UUID, Chat> chats = new HashMap<>();
    private static final ApiClient apiClient = new ApiClient(new StandardRequestStrategy(ApiConfig.BASE_API));

    public static Chat getChat(UUID uuid) {
        return chats.get(uuid);
    }

    public static Chat searchChat(UUID uuid) {
        for (Chat chat : chats.values()) {
            if (chat.getChatId().equals(uuid)) {
                return chat;
            }
        }
        return null;
    }

    public static boolean deleteChat(UUID uuid) {
        Chat chat = searchChat(uuid);
        if (chat != null) {
            chats.remove(uuid);
            return true;
        }
        return false;
    }

    public static boolean addChat(Chat chat) {
        if (searchChat(chat.getChatId()) == null) {
            chats.put(chat.getChatId(), chat);
            return true;
        }
        return false;
    }

    public static List<Chat> getChats() {
        return new ArrayList<>(chats.values());
    }

    public static CustomResponseEntity<List<ChatQuery>> getChatsForUser(String userId) {
        String url = String.format("/chats?userId=%s&page=0&size=10", userId);
        CustomResponseEntity<List<ChatQuery>> response = apiClient.get(url, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }
        
        List<ChatQuery> chatQueries = response.getData();

        for (ChatQuery chatQuery : chatQueries) {
            UUID chatId = chatQuery.getChatId();
            String title = chatQuery.getTitle();
            String description = chatQuery.getDescription();
            UUID userIdNew = chatQuery.getUserId();

            // Build ChatDetail
            ChatDetail chatDetail = new ChatDetail(chatId, null, title, userIdNew);

            // Construct Chat
            Chat chat = new Chat();
            chat.setChatId(chatId);
            chat.setChatDetail(chatDetail);
            // Messages and instructions will be set later if needed

            // Add to static map
            chats.put(chatId, chat);
        }

        return response;
    }
    
    public static boolean deleteChatById(UUID chatId) {
        CustomResponseEntity<Void> response = apiClient.delete("/chats/" + chatId, null);
        
        // Remove from local cache
        chats.remove(chatId);
        return true;
    }
    
    public static CustomResponseEntity<ChatQuery> updateChatTitle(UUID chatId, String title) {
        CustomResponseEntity<ChatQuery> response = apiClient.put("/chats/" + chatId + "/title", title, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }
        
        ChatQuery updatedChat = response.getData();

        UUID chatIdNew = updatedChat.getChatId();
        String titleNew = updatedChat.getTitle();
        String descriptionNew = updatedChat.getDescription();
        UUID userIdNew = updatedChat.getUserId();

        // Build ChatDetail
        ChatDetail chatDetail = new ChatDetail(chatIdNew, null, title, userIdNew);

        // Construct Chat
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setChatDetail(chatDetail);
        
        chats.put(chatId, chat);
        return response;
    }

    public boolean sendMessage(UUID chatId, ChatMessage message) throws Exception {
        Chat chat = chats.get(chatId);
        if (chat != null) {
            chat.addChatMessage(message); // assuming Chat has addMessage()
            return true;
        }
        return false;
    }

    public static String displayChats() {
        String output = "";
        for (Chat chat : chats.values()) {
            output += chat.toString() + "\n";
        }
        return output;
    }

    public static boolean downloadChat(UUID uuid) throws IOException {
        PrintWriter output = null;
        File file = new File("chats/" + uuid + ".txt");
        if (file.exists()) {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            output.println(displayChats());
            fw.close();
            pw.close();
            return true;


        }
        throw new ExportException("No file found");
    }

    public static int calculateTokenCount(String message) {
        if (message == null || message.trim().isEmpty()) {
            return 0;
        }

        String[] words = message.trim().split("\\s+"); // kelimelere ayır
        int tokenCount = 0;

        for (String word : words) {
            if (word.length() <= 6) {
                tokenCount += 1;
            } else {
                tokenCount += (int) Math.ceil(word.length() / 6.0); // 6'ya bölüp yukarı yuvarla
            }
        }

        return tokenCount;
    }

    public static CustomResponseEntity<ChatQuery> createNewChat(ChatRequest chatRequest) {
        CustomResponseEntity<ChatQuery> response = apiClient.post("/chats", chatRequest, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }
        
        return response;
    }
}
