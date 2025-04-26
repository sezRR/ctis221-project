package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.login;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class LoginViewController {
    private AuthService authService;

    public void setAuthService(AuthService service) {
        this.authService = service;
    }

    @FXML
    private void onGoogleLoginButtonClick(ActionEvent event) {
        authService.startAuthFlow();
    }
    
    @FXML
    private void onGuestLoginButtonClick(MouseEvent event) {
        SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
    }
}