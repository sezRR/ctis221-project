package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ChatHeaderComponent extends HBox {
    @FXML private Label chatTitleLabel;
    @FXML private Button downloadButton;
    @FXML private Button settingsButton;

    public ChatHeaderComponent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/chat/ChatHeaderComponent.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ChatHeaderComponent.fxml", e);
        }
    }

    public void setTitle(String title) {
        chatTitleLabel.setText(title);
    }

    public void setButtonsDisabled(boolean disabled) {
        downloadButton.setDisable(disabled);
        settingsButton.setDisable(disabled);
    }

    public Button getDownloadButton() {
        return downloadButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }
}
