package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;

public class HelloController {
    @FXML
    private Label welcomeText;

    public void init() {
//        TokenStore.load().map(stored -> {
//            long now = System.currentTimeMillis() / 1000;
//            if (now < JwtUtils.expires(stored.idToken())) {
//                Platform.runLater(() -> onSuccess.accept(JwtUtils.preferredUsername(stored.idToken())));
//                return true;
//            }
//            try {
//                TokenResponse fresh = RefreshTokenClient.refresh(stored.refreshToken(), TOKEN_URL, CLIENT_ID);
//                TokenStore.save(fresh);
//                Platform.runLater(() -> onSuccess.accept(JwtUtils.preferredUsername(fresh.idToken())));
//                return true;
//            } catch (Exception ignored) {
//                return false;
//            }
//        }).orElse(false);

        // SET THE USERNAME
        String username = TokenStore.load()
                .map(stored -> JwtUtils.preferredUsername(stored.idToken()))
                    .orElseThrow(() -> new RuntimeException("Invalid token found"));
        
        // Set the welcome text
        welcomeText.setText("Welcome " + username + "!");
    }

    @FXML
    private void onLogoutButtonClick(ActionEvent event) {
        SceneManager.switchScene("login-view.fxml");
        // Delete the auth token
        TokenStore.delete();
    }
}