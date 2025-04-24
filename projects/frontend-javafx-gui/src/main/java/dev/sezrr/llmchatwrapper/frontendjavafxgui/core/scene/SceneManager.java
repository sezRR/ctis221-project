package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static Stage primaryStage;
    private static final Map<String, SceneControllerPair> scenes = new HashMap<>();

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    @SuppressWarnings("unchecked")
    public static <T> T switchScene(String fxmlName) {
        try {
            SceneControllerPair pair = scenes.computeIfAbsent(fxmlName, name -> {
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource(name));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Object controller = loader.getController();
                    return new SceneControllerPair(scene, controller);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to load FXML: " + name, e);
                }
            });
            primaryStage.setScene(pair.scene);
            return (T) pair.controller;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class SceneControllerPair {
        Scene scene;
        Object controller;
        SceneControllerPair(Scene scene, Object controller) {
            this.scene = scene;
            this.controller = controller;
        }
    }
}