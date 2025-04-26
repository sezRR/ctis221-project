package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.AuthController;
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
        
        
        AuthController authController = SceneManager.switchScene("login-view.fxml");
        authController.setAuthService(new AuthService(getHostServices()));
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}