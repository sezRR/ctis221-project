package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.custom_response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.request.ApiClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.request.StandardRequestStrategy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        
        // Send request to the http://localhost:8080/api/v1/models
        String baseApiUrl = "http://localhost:8080/api/v1";
        ApiClient apiClient = new ApiClient(new StandardRequestStrategy(baseApiUrl));
        CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {});
        response.getData().forEach(System.out::println);
    }

    public static void main(String[] args) {
        launch();
    }
}