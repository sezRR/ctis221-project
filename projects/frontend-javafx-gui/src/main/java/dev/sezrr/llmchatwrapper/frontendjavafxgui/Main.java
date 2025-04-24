package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth.AuthController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth.AuthService;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
        
        // Send request to the http://localhost:8080/api/v1/models
//        String baseApiUrl = "http://localhost:8080/api/v1";
//        ApiClient apiClient = new ApiClient(new StandardRequestStrategy(baseApiUrl));
//        CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {});
//        response.getData().forEach(System.out::println);
        
        // Scene Manager Initialization
        // SceneManager.init(stage);
        SceneManager.init(stage);
        stage.setTitle("LLM Chat Wrapper");
        // Load the login scene and retrieve its controller
        AuthController authController = SceneManager.switchScene("login-view.fxml");
        // Inject HostServices into AuthController via AuthService
        authController.setAuthService(new AuthService(getHostServices(), username -> {;
            // Handle successful authentication here
            System.out.println("Authentication successful for user: " + username);
            // Switch to the main application scene
            HelloController helloController = SceneManager.switchScene("hello-view.fxml");
            helloController.init();
            
        }));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}