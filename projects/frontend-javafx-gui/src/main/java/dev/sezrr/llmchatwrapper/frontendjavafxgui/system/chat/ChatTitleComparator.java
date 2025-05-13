package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import java.util.Comparator;

public class ChatTitleComparator implements Comparator<Chat> {
    public int compare(Chat chat1, Chat chat2) {
        return chat1.getChatDetail().getTitle().compareTo(chat2.getChatDetail().getTitle());
    }
}