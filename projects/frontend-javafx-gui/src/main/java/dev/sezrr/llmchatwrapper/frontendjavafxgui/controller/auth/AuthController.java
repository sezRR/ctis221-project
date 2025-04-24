package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.HelloController;

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