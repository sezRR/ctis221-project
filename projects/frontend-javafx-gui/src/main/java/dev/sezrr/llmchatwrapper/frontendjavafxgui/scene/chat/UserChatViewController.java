package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert.AlertUtil;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenStore;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.JwtUtils;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component.ChatComponent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component.ChatHeaderComponent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component.ChatMessageComponent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component.NoChatSelectedComponent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.enums.MessageSender;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatMessageQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.MessageType;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin.AdminViewModelController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat.ChatSystem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Objects;

public class UserChatViewController {
    @FXML
    private Label username;

    @FXML
    private ComboBox<String> currentView;

    @FXML
    private VBox chatsContent;

    @FXML
    private VBox chatMessagesContainer;

    @FXML
    private VBox chatViewContainer;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button messageSendButton;

    @FXML
    private ComboBox<String> modelsCombo;

    private final NoChatSelectedComponent noChatSelectedComponent = new NoChatSelectedComponent();
    private final ChatHeaderComponent chatHeaderComponent = new ChatHeaderComponent();

    @FXML
    private BorderPane chatScreenContainer;

    @FXML
    private ScrollPane scrollpaneContainer;

    private final HBox emptyPlaceholder = new HBox();

    private boolean isChatSelected = false;

    private String selectedModel;

    private String selectedChatId = null;

    private void initEmptyPlaceholder() {
        if (!chatsContent.getChildren().contains(emptyPlaceholder)) {
            FontIcon icon = new FontIcon("fas-comments");
            icon.setIconSize(18);
            icon.setIconColor(Paint.valueOf("gray"));

            Label label = new Label("Create a chat to start");
            label.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");

            emptyPlaceholder.getChildren().setAll(icon, label);
            emptyPlaceholder.setSpacing(8);
            emptyPlaceholder.setStyle("-fx-alignment: center;");
            emptyPlaceholder.setPadding(new Insets(12));
            emptyPlaceholder.setVisible(false);

            chatsContent.getChildren().add(emptyPlaceholder);
        }
    }

    public void init() {
        var tokens = TokenStore.load();
        if (tokens.isPresent()) {
            init(JwtUtils.preferredName(tokens.get().idToken()));
        } else {
            init("Guest");
        }
    }

    private void updateEmptyPlaceholderVisibility() {
        long visibleChats = chatsContent.getChildren().stream()
                .filter(node -> node instanceof ChatComponent)
                .count();
        emptyPlaceholder.setVisible(visibleChats == 0);
    }

    public void init(String username) {
        this.username.setText(username);
        initComboBox();

        chatsContent.getChildren().clear(); // Clear any existing components

        String userId = "0196b9f6-645f-715f-b30a-7178d650354b";
        var tokens = TokenStore.load();
        if (tokens.isPresent()) {
            userId = JwtUtils.userId(tokens.get().idToken());
        }

        try {
            CustomResponseEntity<List<ChatQuery>> response = ChatSystem.getChatsForUser(userId);
            if (response != null && response.isSuccess()) {
                for (ChatQuery chat : response.getData()) {
                    chatsContent.getChildren().add(createChatComponent(chat));
                }
            } else {
                AlertUtil.showError("Failed to load chats", "Could not retrieve user chats.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError("Error", "An error occurred while loading chats.");
        }

        initEmptyPlaceholder();
        initNoChatSelectedComponent();
        initChatHeaderComponent();
        updateViews(false);

        messageTextField.setOnKeyPressed(e -> {
            if (e.getCode().getName().equals("Enter")) {
                sendMessage(new ActionEvent());
            }
        });

        initModelsCombo();
    }

    private void initNoChatSelectedComponent() {
        if (!chatMessagesContainer.getChildren().contains(noChatSelectedComponent)) {
            chatMessagesContainer.getChildren().add(noChatSelectedComponent);
        }
        toggleNoChatSelectedComponent();
        noChatSelectedComponent.setOnExampleClicked(example -> {
            messageTextField.setText(example);
        });
    }

    private void toggleNoChatSelectedComponent() {
        System.out.println(isChatSelected);
        if (isChatSelected) {
            noChatSelectedComponent.setVisible(false);
            noChatSelectedComponent.setManaged(false);
        } else {
            noChatSelectedComponent.setVisible(true);
            noChatSelectedComponent.setManaged(true);
        }
    }

    private void initChatHeaderComponent() {
        chatHeaderComponent.setTitle("New Chat");
//        chatHeaderComponent.setModel("ChatGPT 4o-mini"); // TODO: FIX
        chatHeaderComponent.setButtonsDisabled(false);
        chatHeaderComponent.setVisible(false);
        chatHeaderComponent.setManaged(false);
        chatScreenContainer.setTop(chatHeaderComponent);
    }

    public void setCurrentViewValue(View value) {
        currentView.setValue(value.getView());
    }

    private void initModelsCombo() {
        modelsCombo.getItems().clear();

        List<String> models;
        try {
            models = ChatSystem.getAvailableModels();
            messageSendButton.setDisable(models.isEmpty());
            messageTextField.setDisable(models.isEmpty());
            if (!models.isEmpty()) {
                modelsCombo.getItems().addAll(models);
            } else {
                AlertUtil.showError("Failed to load models", "Could not retrieve models. Message sending is disabled.");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError("Error", "An error occurred while loading models. Message sending is disabled.");
            return;
        }

        modelsCombo.setValue(models.getFirst()); // Set default value
        this.selectedModel = modelsCombo.getValue();
        modelsCombo.setOnAction(event -> {
            String selectedModel = modelsCombo.getValue();
            if (selectedModel == null)
                return;

            this.selectedModel = selectedModel;
        });
    }

    private void initComboBox() {
        // Remove all items from the ComboBox
        currentView.getItems().clear();

        currentView.getItems().addAll(View.USER_VIEW.getView(), View.ADMIN_VIEW.getView());
        currentView.setValue(View.USER_VIEW.getView()); // Set default value

        currentView.setOnAction(event -> {
            String selectedView = currentView.getValue();
            if (selectedView == null) {
                return;
            }

            updateViews(false);
            if (selectedView.equals(View.USER_VIEW.getView())) {
                // Switch to User View
                SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
            } else if (selectedView.equals(View.ADMIN_VIEW.getView())) {
                // Switch to Admin View
                AdminViewModelController adminViewModelController = SceneManager.switchScene(SceneConstant.ADMIN_MODEL_VIEW);
                adminViewModelController.init();
            }
        });
    }

    private void updateViews(boolean isChatSelected) {
        updateViews(isChatSelected, false);
    }

    private void updateViews(boolean isChatSelected, boolean forceToggle) {
        this.isChatSelected = isChatSelected;

        if (!isChatSelected || forceToggle) {
            removeMessages();
            selectedChatId = null;
        }

        updateEmptyPlaceholderVisibility();
        updateAlignmentChatViewContainer();
        toggleNoChatSelectedComponent();
        toggleChatHeaderComponent();
    }

    @FXML
    private void signOut(ActionEvent e) {
        updateViews(false);
        TokenStore.delete();
        // Switch back to the login view
        SceneManager.switchScene(SceneConstant.LOGIN_VIEW);
    }

    @FXML
    private void createNewChat(ActionEvent e) {
        updateViews(false, true);
        chatHeaderComponent.setTitle("New Chat");

        messageTextField.setText("");
        selectedChatId = null;

        // TODO: WHEN USER SEND A MESSAGE, THEN CREATE A CHAT
    }

    private void removeMessages() {
        chatMessagesContainer.getChildren().clear();
        chatMessagesContainer.getChildren().add(noChatSelectedComponent);
    }

    private void toggleChatHeaderComponent() {
        if (isChatSelected) {
            chatHeaderComponent.setVisible(true);
            chatHeaderComponent.setManaged(true);
        } else {
            chatHeaderComponent.setVisible(false);
            chatHeaderComponent.setManaged(false);
        }
    }

    public void forceReRenderMessages(List<ChatMessageQuery> messages) {
        chatMessagesContainer.getChildren().clear();
        for (var message : messages) {
            if (message.getSenderRole() == MessageType.ASSISTANT) {
                chatMessagesContainer.getChildren().add(new ChatMessageComponent(message.getMessage(), message.getModel(), MessageSender.ASSISTANT));
            } else {
                chatMessagesContainer.getChildren().add(new ChatMessageComponent(message.getMessage(), message.getModel(), MessageSender.USER));
            }
        }
    }

    private ChatComponent createChatComponent(ChatQuery chat) {
        ChatComponent component = new ChatComponent();
        component.setChatTitle(chat.getTitle());
        component.setChatId(chat.getChatId());
        component.setOnDeleted(deletedComponent -> updateViews(false));
        component.setOnTitleUpdated(updatedComponent -> {
            chatHeaderComponent.setTitle(updatedComponent.getChatTitle());
        });
        component.setOnClick(chatComponent -> {
            updateViews(true, true);
            chatHeaderComponent.setTitle(chatComponent.getChatTitle());
            chatHeaderComponent.setButtonsDisabled(false);
            selectedChatId = chatComponent.getChatId().toString();
            try {
                var response = ChatSystem.getMessages(chatComponent.getChatId(), null, 50);
                if (response != null && response.isSuccess()) {
                    forceReRenderMessages(response.getData().getContent());
                } else {
                    AlertUtil.showError("Failed to load messages", "Could not retrieve chat messages.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showError("Error", "An error occurred while loading messages.");
            }

            Platform.runLater(() -> {
                scrollpaneContainer.setVvalue(1.0); // Keep scroll at bottom
            });
        });

        return component;
    }

    private void updateAlignmentChatViewContainer() {
        if (!isChatSelected)
            chatViewContainer.setAlignment(Pos.CENTER);
        else
            chatViewContainer.setAlignment(Pos.TOP_LEFT);
    }

    @FXML
    private void sendMessage(ActionEvent e) {
        String message = messageTextField.getText();
        if (message.isEmpty()) {
            AlertUtil.showError("Empty Message", "Please enter a message before sending.");
            return;
        }

        String userId = "0196b9f6-645f-715f-b30a-7178d650354b";
        var tokens = TokenStore.load();
        if (tokens.isPresent()) {
            userId = JwtUtils.userId(tokens.get().idToken());
        }

        ChatRequest chatRequest = new ChatRequest(
                userId,
                "New Chat",
                "Description"
        );

        ChatQuery chatQuery = null;
        if (selectedChatId == null) {
            try {
                CustomResponseEntity<ChatQuery> response = ChatSystem.createNewChat(chatRequest);
                if (response == null || !response.isSuccess()) {
                    AlertUtil.showError("Failed to create new chat", "Something went wrong while creating a new chat.");
                } else {
                    chatQuery = response.getData();

                    // Create and configure the component
                    ChatComponent component = createChatComponent(chatQuery);

                    // Add it to the top of the VBox
                    chatsContent.getChildren().addFirst(component);
                    updateViews(true);
                    selectedChatId = chatQuery.getChatId().toString();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                AlertUtil.showError("Failed to set model", "Something went wrong while creating the new chat.");
            }
        } else {
            chatQuery = Objects.requireNonNull(ChatSystem.getChatById(selectedChatId)).getData();
        }

        // TODO: MESSAGE LOGIC
        // For now, just add the message to the chat messages container
        chatMessagesContainer.getChildren().addAll(
                new ChatMessageComponent(message, "User", MessageSender.USER)
        );
        messageTextField.clear();
        updateViews(true);

        if (selectedModel == null) {
            AlertUtil.showError("Model not selected", "Please select a model before sending a message.");
            return;
        }

        if (chatQuery == null) {
            AlertUtil.showError("Chat not created", "Please try sending the message again in a new chat.");
            return;
        }

//        ChatMessageComponent newMessage = new ChatMessageComponent("..... Waiting response..", selectedModel, MessageSender.ASSISTANT);
        ChatMessageComponent newMessage = new ChatMessageComponent("", selectedModel, MessageSender.ASSISTANT);
        chatMessagesContainer.getChildren().addAll(newMessage);

        try {
            // Disable input during streaming
            messageTextField.setDisable(true);
            messageSendButton.setDisable(true);

//            newMessage.setMessageText(""); // Clear input area

            ChatQuery finalChatQuery = chatQuery;
            ChatSystem.sendMessageToChat(
                    chatQuery.getChatId(),
                    selectedModel,
                    message,
                    (newLlmMessage) -> {
                        newMessage.appendMessageText(newLlmMessage);
                        // scroll
                        Platform.runLater(() -> {
                            scrollpaneContainer.setVvalue(1.0); // Keep scroll at bottom
                        });
                    },
                    error -> {
                        error.printStackTrace();
                        AlertUtil.showError("Streaming Error", "An error occurred while receiving the response:\n" + error.getMessage());

                        // Re-enable UI on error
                        messageTextField.setDisable(false);
                        messageSendButton.setDisable(false);
                    },
                    () -> {
                        System.out.println("Stream completed");
                        newMessage.finalizeMessage(); // Re-render markdown properly
                        Platform.runLater(() -> {
                            scrollpaneContainer.setVvalue(1.0); // Keep scroll at bottom
                            messageTextField.setDisable(false);
                            messageSendButton.setDisable(false);
                        });
                    });

            Platform.runLater(() -> {
                scrollpaneContainer.setVvalue(1.0); // Keep scroll at bottom
            });
            
        } catch (Exception exception) {
            exception.printStackTrace();
            AlertUtil.showError("Failed to send message", "Something went wrong while sending the message.");

            // Re-enable UI on immediate failure
            messageTextField.setDisable(false);
            messageSendButton.setDisable(false);
        }
    }

    public enum View {
        USER_VIEW("User View"),
        ADMIN_VIEW("Admin View");

        private final String view;

        View(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }
}
