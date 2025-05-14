package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.App;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.LlmChatWrapper;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert.AlertUtil;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat.ChatSystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatComponent extends HBox {
    private UUID chatId;
    
    @FXML
    private Label chatTitle;

    @FXML
    private FontIcon contextMenuIcon;

    private TextField titleEditor = new TextField();

    private Consumer<ChatComponent> onDeleted;
    private Consumer<ChatComponent> onTitleUpdated;
    private Consumer<ChatComponent> onClick;
    
    public void setOnDeleted(Consumer<ChatComponent> onDeleted) {
        this.onDeleted = onDeleted;
    }
    
    public void setOnTitleUpdated(Consumer<ChatComponent> onTitleUpdated) {
        this.onTitleUpdated = onTitleUpdated;
    }
    
    public void setOnClick(Consumer<ChatComponent> onClick) {
        this.onClick = onClick;
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                onClick.accept(this);
            }
        });
    }

    private void showTitleEditor() {
        titleEditor.setText(chatTitle.getText());
        titleEditor.setStyle("-fx-font-size: 14px;");
        titleEditor.setPrefWidth(chatTitle.getWidth());

        int titleIndex = this.getChildren().indexOf(chatTitle);
        this.getChildren().remove(chatTitle);
        this.getChildren().add(titleIndex, titleEditor);
        titleEditor.requestFocus();

        // Confirm on Enter
        titleEditor.setOnAction(e -> confirmTitleEdit());

        // Cancel on focus lost or Esc
        titleEditor.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelTitleEdit();
            }
        });

        titleEditor.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) cancelTitleEdit();
        });
    }

    private void confirmTitleEdit() {
        String newTitle = titleEditor.getText().trim();
        if (newTitle.length() > 20) {
            AlertUtil.showError("Error", "Title cannot exceed 20 characters.");
            return;
        }
        if (!newTitle.isEmpty()) {
            try {
                CustomResponseEntity<ChatQuery> updatedChat = ChatSystem.updateChatTitle(chatId, newTitle);
                if (updatedChat == null || !updatedChat.isSuccess()) {
                    AlertUtil.showError("Error", "Failed to update chat title.");
                } else {
                    chatTitle.setText(updatedChat.getData().getTitle());
                    if (onTitleUpdated != null) {
                        onTitleUpdated.accept(this);
                    }
                    AlertUtil.showInfo("Success", "Title updated.");
                }   
            } catch (Exception e) {
                AlertUtil.showError("Error", "Failed to update chat title.");
            }
        }
        swapBackToLabel();
    }

    private void cancelTitleEdit() {
        swapBackToLabel();
    }

    private void swapBackToLabel() {
        // Remove the editor if it's in the children
        this.getChildren().remove(titleEditor);

        // Only add chatTitle if it's not already present
        if (!this.getChildren().contains(chatTitle)) {
            this.getChildren().addFirst(chatTitle); // or use preferred index
        }
    }

    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        // Create Update Title menu item with icon and spacing
        FontIcon updateIcon = new FontIcon("fas-pen");
        updateIcon.setIconSize(8);
        Label updateLabel = new Label("Update Title");
        HBox updateBox = new HBox(8, updateIcon, updateLabel); // 4px spacing
        MenuItem updateItem = new MenuItem();
        updateItem.setGraphic(updateBox);
        updateItem.setOnAction(e -> {
            System.out.println("Update title clicked for: " + getChatTitle());
            showTitleEditor();
        });

        // Create Delete Chat menu item with icon and spacing
        FontIcon deleteIcon = new FontIcon("fas-trash");
        deleteIcon.setIconSize(8);
        Label deleteLabel = new Label("Delete Chat");
        HBox deleteBox = new HBox(8, deleteIcon, deleteLabel); // 4px spacing
        MenuItem deleteItem = new MenuItem();
        deleteItem.setGraphic(deleteBox);
        deleteItem.setOnAction(e -> {
            System.out.println("Delete chat clicked for: " + getChatTitle());

            if (ChatSystem.deleteChatById(chatId)) {
                if (this.getParent() instanceof VBox parent) {
                    parent.getChildren().remove(this); // ✅ Safe removal
                    if (onDeleted != null) {
                        System.out.println("Invoking onDeleted callback...");
                        onDeleted.accept(this);
                        AlertUtil.showSuccess("Success", "Chat deleted successfully.");
                    } else {
                        System.out.println("onDeleted is null");
                    }
                }
            } else {
                System.out.println("Failed to delete chat with ID: " + chatId);
            }
        });

        // Add items to context menu
        contextMenu.getItems().addAll(updateItem, deleteItem);

        // Show on left-click
        contextMenuIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(contextMenuIcon, event.getScreenX(), event.getScreenY());
            }

            // Disable event bubbling to prevent showing the context menu on the icon
            event.consume();
        });
    }

    public ChatComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(LlmChatWrapper.class.getResource("/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/chat/Chat.fxml"));
        fxmlLoader.setRoot(this);           // <--- Important for fx:root
        fxmlLoader.setController(this);      // <--- You are the controller
        this.getStylesheets().add(LlmChatWrapper.class.getResource("/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/chat/chat.css").toExternalForm());
        try {
            fxmlLoader.load();
            setupContextMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setChatTitle(String title) {
        if (chatTitle != null) {
            chatTitle.setText(title);
        }
    }

    public String getChatTitle() {
        return chatTitle != null ? chatTitle.getText() : "";
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getChatId() {
        return this.chatId;
    }
}
