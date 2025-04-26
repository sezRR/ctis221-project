package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenStore;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin.AdminViewModelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class UserChatViewController {
    @FXML
    private Label username;
    
    @FXML
    private ComboBox<String> currentView;
    
    public void init(String username) {
        this.username.setText(username);
        
        initComboBox();
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
