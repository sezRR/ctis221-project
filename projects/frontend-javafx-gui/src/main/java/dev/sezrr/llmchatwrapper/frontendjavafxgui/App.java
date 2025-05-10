package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.login.LoginViewController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Send request to the http://localhost:8080/api/v1/models
//        String baseApiUrl = "http://localhost:8080/api/v1";
//        ApiClient apiClient = new ApiClient(new StandardRequestStrategy(baseApiUrl));
//        CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {});
//        response.getData().forEach(System.out::println);
        
        // Scene Manager Initialization
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