package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;

import java.util.function.Consumer;

public class AuthController {
    private AuthService authService;

    public void setAuthService(AuthService service) {
        this.authService = service;
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        authService.startAuthFlow();
    }
}