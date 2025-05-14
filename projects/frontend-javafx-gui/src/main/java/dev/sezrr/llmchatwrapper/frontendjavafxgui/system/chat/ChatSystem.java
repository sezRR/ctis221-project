package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.*;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatMessageQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.pagination.CursorPaginationResponse;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import javafx.application.Platform;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.server.ExportException;
import java.util.*;
import java.util.function.Consumer;

public class ChatSystem {
    private static Map<UUID, Chat> chats = new HashMap<>();
    private static Map<UUID, List<ChatMessageQuery>> messages = new HashMap<>(); // Cache for messages
    private static final ApiClient apiClient = new ApiClient(new StandardRestRequestStrategy(ApiConfig.BASE_API), new StandardStreamingRequestStrategy(ApiConfig.BASE_API));

    public static void clearCache() {
        chats.clear();
        messages.clear();
    }
    
    public static Map<UUID, Chat> getChats() {
        return chats;
    }

    public static Map<UUID, List<ChatMessageQuery>> getMessages() {
        return messages;
    }

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
            chat.getChatDetail().setCreatedAt(chatQuery.getCreatedAt());
            // Messages and instructions will be set later if needed
            
            // Messages will be fetched separately for each chat TODO: Performance improvement
            var messages = getMessages(chatId, null, 55);
            if (messages != null && messages.isSuccess()) {
                chat.setMessages(messages.getData().getContent());
            } else {
                chat.setMessages(new ArrayList<>());
            }
            
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
    
    public static List<String> getAvailableModels() {
        CustomResponseEntity<List<String>> response = apiClient.get("/providers/openrouter/models", new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess() || response.getData() == null || response.getData().isEmpty()) {
            return List.of();
        }
        
        return response.getData();
    }

    public static void sendMessageToChat(UUID chatId, String model, String message,
                                         Consumer<String> onNext,
                                         Consumer<Throwable> onError,
                                         Runnable onComplete) {
        Flux<String> stream = apiClient.stream(
                "/providers/openrouter/" + chatId + "/chats/" + chatId + "?model=" + model,
                message
        );

        stream.subscribe(
                token -> Platform.runLater(() -> onNext.accept(token)),   // Ensure UI-safe message update
                error -> Platform.runLater(() -> onError.accept(error)),  // Ensure UI-safe error handling
                () -> Platform.runLater(onComplete)                       // Ensure UI-safe completion
        );
    }
    
    public static CustomResponseEntity<ChatQuery> getChatById(String chatId) {
        CustomResponseEntity<ChatQuery> response = apiClient.get("/chats/" + chatId, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }
        
        ChatQuery chatQuery = response.getData();
        UUID chatIdNew = chatQuery.getChatId();
        String titleNew = chatQuery.getTitle();
        String descriptionNew = chatQuery.getDescription();
        UUID userIdNew = chatQuery.getUserId();

        // Build ChatDetail
        ChatDetail chatDetail = new ChatDetail(chatIdNew, null, titleNew, userIdNew);

        // Construct Chat
        Chat chat = new Chat();
        chat.setChatId(chatIdNew);
        chat.setChatDetail(chatDetail);
        chat.getChatDetail().setCreatedAt(chatQuery.getCreatedAt());
        
        chats.put(chatIdNew, chat);
        return response;
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
        ChatDetail chatDetail = new ChatDetail(chatIdNew, null, titleNew, userIdNew);

        // Construct Chat
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setChatDetail(chatDetail);
        chat.getChatDetail().setCreatedAt(updatedChat.getCreatedAt());
        
        chats.put(chatId, chat);
        return response;
    }

    public static String displayChats() {
        String output = "";
        for (Chat chat : chats.values()) {
            output += chat.toString() + "\n";
        }
        return output;
    }

    public static boolean downloadChat(UUID uuid) throws IOException {
        File file = new File("chats/" + uuid + ".txt");
        if (file.exists()) {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            // write the chats into the file
            pw.println(displayChats());
            // close in reverse order of opening
            pw.close();
            fw.close();
            return true;
        }
        throw new ExportException("No file found");
    }

    public static CustomResponseEntity<ChatQuery> createNewChat(ChatRequest chatRequest) {
        CustomResponseEntity<ChatQuery> response = apiClient.post("/chats", chatRequest, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }

        createNewChat(response.getData().getChatId(), response.getData().getTitle(), UUID.fromString(chatRequest.userId()), response.getData().getCreatedAt());
        
        return response;
    }
    
    private static void createNewChat(UUID chatId, String title, UUID userId, String createdAt) {
        ChatDetail chatDetail = new ChatDetail(chatId, null, title, userId);
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setChatDetail(chatDetail);
        chat.getChatDetail().setCreatedAt(createdAt);
        
        chats.put(chatId, chat);
    }
    
    public static CustomResponseEntity<CursorPaginationResponse<List<ChatMessageQuery>>> getMessages(UUID chatId, UUID beforeMessageId, int size) {
        String url = String.format("/chats/%s/messages?size=%d", chatId, size);
        CustomResponseEntity<CursorPaginationResponse<List<ChatMessageQuery>>> response = apiClient.get(url, new TypeReference<>() {
        });
        
        if (response == null || !response.isSuccess()) {
            return null;
        }
        
        // Cache the messages in the chat object
        messages.put(chatId, response.getData().getContent());
        
        return response;
    }
    
    public static void addMessageToCache(UUID chatId, ChatMessageQuery message) {
        messages.computeIfAbsent(chatId, k -> new ArrayList<>()).add(message);
    }
    
    // ARDA'S SORT
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

    public static Chat searchChatByTitle(String fragment) {
        if (fragment == null) return null;
        String lower = fragment.toLowerCase();
        for (Chat chat : chats.values()) {
            String title = chat.getChatDetail().getTitle();
            if (title != null && title.toLowerCase().contains(lower)) {
                return chat;
            }
        }
        return null;
    }

    public static String sortByCreationDate() {
        ChatDateComparator cdc = new ChatDateComparator();
        TreeSet<Chat> ts = new TreeSet<>(cdc);
        var a = chats.values();
        ts.addAll(a);

        String output = "";
        for (Chat chat : ts) {
            output += chat.toString() + "\n";
        }
        return output;
    }
    
    public static String getNewChatTitle() {
        return "New Chat " + (chats.size() + 1);
    }
    
    public static String sortByTitle() {
        ChatTitleComparator ctc = new ChatTitleComparator();
        TreeSet<Chat> ts = new TreeSet<>(ctc);
        ts.addAll(chats.values());

        String output = "";
        for (Chat chat : ts) {
            output += chat.toString() + "\n";
        }
        return output;
    }

    public static String showTotalMessages() {
        int total = 0;
        for (Chat chat : chats.values()) {
            total += chat.getMessages().size();
        }
        return "Total messages: " + total;
    }

    public static String showTotalTokens() {
        String res = "";
        int totalTokens = 0;

        for (Chat chat : chats.values()) {
            for (ChatMessageQuery msg : chat.getMessages()) {
                totalTokens += calculateTokenCount(msg.getMessage());
            }
        }
        res += "Total tokens across all chats: " + totalTokens;
        return res;
    }
    
    public static int calculateTotalTokensForChat(UUID chatId) {
        int totalTokens = 0;
        List<ChatMessageQuery> chatMessages = messages.get(chatId);
        
        if (chatMessages != null) {
            for (ChatMessageQuery msg : chatMessages) {
                totalTokens += calculateTokenCount(msg.getMessage());
            }
        }
        
        return totalTokens;
    }

    public static List<ChatMessageQuery> getMessagesForChat(UUID chatId) {
        return messages.getOrDefault(chatId, new ArrayList<>());
    }

    public static double getMaxTokens() {
        return Math.pow(2, 15);
    }
}
