package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class NoChatSelectedComponent extends VBox {
    private Consumer<String> onExampleClicked;
    
    public NoChatSelectedComponent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/message/NoChatSelectedComponent.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load NoChatSelectedComponent.fxml", e);
        }

        URL css = getClass().getResource("/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/message/no-chat-selected.css");
        if (css != null) {
            this.getStylesheets().add(css.toExternalForm());
        }
    }
    
    public void setOnExampleClicked(Consumer<String> onExampleClicked) {
        this.onExampleClicked = onExampleClicked;
    }

    @FXML
    private void onExampleClicked(MouseEvent e) {
        Label label = (Label) e.getSource();
        System.out.println("Clicked: " + label.getText());
        if (onExampleClicked != null) {
            onExampleClicked.accept(label.getText());
        }
    }
}
