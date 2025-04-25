package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;

public class AuthController {
    private AuthService authService;

    public void setAuthService(AuthService service) {
        this.authService = service;
    }

    public void setAuthSuccess(String msg) {
        // TODO: REMOVE
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        authService.startAuthFlow(_ -> {
            // After auth success, switch to main scene
            HelloController hello = SceneManager.switchScene("hello-view.fxml");
            hello.init();
        });
    }
}