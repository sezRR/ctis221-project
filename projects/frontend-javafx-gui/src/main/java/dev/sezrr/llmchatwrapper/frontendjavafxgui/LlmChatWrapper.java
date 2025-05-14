package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.login.LoginViewController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.*;

import java.io.IOException;

public class LlmChatWrapper extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        
        System.out.println("Starting LLM Chat Wrapper...");
        
        SceneManager.init(stage);
        stage.setTitle("LLM Chat Wrapper");
        
        LoginViewController loginViewController = SceneManager.switchScene(SceneConstant.LOGIN_VIEW);
        loginViewController.setAuthService(new AuthService(getHostServices()));
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}