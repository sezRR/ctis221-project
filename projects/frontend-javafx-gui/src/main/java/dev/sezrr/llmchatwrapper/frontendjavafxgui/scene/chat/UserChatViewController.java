package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert.AlertUtil;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenStore;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.JwtUtils;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component.ChatComponent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ChatRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin.AdminViewModelController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat.ChatSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class UserChatViewController {
    @FXML
    private Label username;
    
    @FXML
    private ComboBox<String> currentView;
    
    @FXML
    private ScrollPane chatsContainer;
    
    @FXML
    private VBox chatsContent;

    private final HBox emptyPlaceholder = new HBox();

    private void setupEmptyPlaceholder() {
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
            CustomResponseEntity<java.util.List<ChatQuery>> response = ChatSystem.getChatsForUser(userId);
            if (response != null && response.isSuccess()) {
                for (ChatQuery chat : response.getData()) {
                    ChatComponent component = new ChatComponent();
                    component.setChatTitle(chat.getTitle());
                    component.setChatId(chat.getChatId());
                    component.setOnDeleted(deletedComponent -> updateEmptyPlaceholderVisibility());
                    chatsContent.getChildren().add(component);
                }
            } else {
                AlertUtil.showError("Failed to load chats", "Could not retrieve user chats.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError("Error", "An error occurred while loading chats.");
        }

        setupEmptyPlaceholder();
        updateEmptyPlaceholderVisibility();
    }

    
    public void setCurrentViewValue(View value) {
        currentView.setValue(value.getView());
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
    
    @FXML
    private void signOut(ActionEvent e) {
        TokenStore.delete();
        // Switch back to the login view
        SceneManager.switchScene(SceneConstant.LOGIN_VIEW);
    }

    @FXML
    private void createNewChat(ActionEvent e) {
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

        try {
            CustomResponseEntity<ChatQuery> response = ChatSystem.createNewChat(chatRequest);
            if (response == null || !response.isSuccess()) {
                AlertUtil.showError("Failed to create new chat", "Something went wrong while creating a new chat.");
            } else {
                ChatQuery chat = response.getData();

                // Create and configure the component
                ChatComponent component = new ChatComponent();
                component.setChatTitle(chat.getTitle());
                component.setChatId(chat.getChatId());
                component.setOnDeleted(deletedComponent -> updateEmptyPlaceholderVisibility());

                // Add it to the top of the VBox
                chatsContent.getChildren().addFirst(component);
                updateEmptyPlaceholderVisibility();
                AlertUtil.showSuccess("New Chat Created", "A new chat has been created successfully.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertUtil.showError("Failed to set model", "Something went wrong while creating the new chat.");
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
