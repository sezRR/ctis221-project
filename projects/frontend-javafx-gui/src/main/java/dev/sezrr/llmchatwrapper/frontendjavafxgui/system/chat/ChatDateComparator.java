package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import java.util.Comparator;

public class ChatDateComparator implements Comparator<Chat> {
    public int compare(Chat chat1, Chat chat2) {
        return chat1.getChatDetail().getCreatedAt().compareTo(chat2.getChatDetail().getCreatedAt());
    }
}