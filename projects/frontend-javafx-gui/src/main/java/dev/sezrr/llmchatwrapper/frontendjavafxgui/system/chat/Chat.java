package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat {
    private UUID chatId;
    private ChatDetail ChatDetail;
    private ArrayList<ChatMessage> messages = new ArrayList<>();
    private ArrayList<ChatInstruction> instructions = new ArrayList<>();

    public Chat()
    {

    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public Chat(ChatDetail chatDetail, ArrayList<ChatInstruction> instructions, ArrayList<ChatMessage> messages) {
        ChatDetail = chatDetail;
        this.instructions = instructions;
        this.messages = messages;
    }

    public ChatDetail getChatDetail() {
        return ChatDetail;
    }

    public void setChatDetail(ChatDetail chatDetail) {
        ChatDetail = chatDetail;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<ChatInstruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<ChatInstruction> instructions) {
        this.instructions = instructions;
    }

    public boolean controlMessages(ChatMessage message)
    {
        for (ChatMessage chatMessage : messages) {
            if (chatMessage.equals(message))
                return true;
        }
        return false;
    }

    public boolean addChatMessage(ChatMessage message) throws Exception {
        if(!controlMessages(message))
        {
            messages.add(message);
            return true;
        }
        throw new Exception("\nThere is already a chat in the Chat List");
    }

    public boolean removeChatMessage(UUID uuid) throws Exception {
        ChatMessage mes;
        for (int i = 0; i < messages.size(); i++)
        {
            mes = messages.get(i);
            if(mes.getChatId()==uuid)
            {
                messages.remove(mes);
                return true;
            }
        }
        throw new Exception("\nThere is no chat in the Chat List that can be deleted!");
    }

public void setChatMessages(List<ChatMessage> messages)  {
    this.messages= (ArrayList<ChatMessage>) messages;
}

    public boolean controlInstructions(ChatInstruction instruction)
    {
        for (ChatInstruction chatInstruction : instructions) {
            if (chatInstruction.equals(instruction))
                return true;
        }
        return false;
    }

    public boolean addInstructions(ChatInstruction instruction) throws Exception {
        if(!controlInstructions(instruction))
        {
            instructions.add(instruction);
            return true;
        }
        throw new Exception("\nThere is already a chat in the Chat Instruction");
    }

    public boolean removeInstruction(UUID uuid) throws Exception {
        ChatInstruction ins;
        for (ChatInstruction instruction : instructions) {
            ins = instruction;
            if (ins.getChatId() == uuid)
                instructions.remove(ins);
            return true;
        }
        throw new Exception("\nThere is no instructions in the Instruction List that can be deleted!");
    }

    public void setInstructions(List<ChatInstruction> instructions)  {
        this.instructions= (ArrayList<ChatInstruction>) instructions;
    }

    @Override
    public String toString() {
        return "\nChat{" +
                "\nChatDetail=" + ChatDetail +
                "\nChatId=" + chatId +
                "\nMessages=" + messages +
                "\nInstructions=" + instructions +
                '}';
    }
}