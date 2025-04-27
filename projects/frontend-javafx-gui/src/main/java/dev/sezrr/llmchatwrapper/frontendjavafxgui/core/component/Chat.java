package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class Chat extends HBox {
    @FXML
    private Label chatTitle;

    public Chat() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/components/chat/chat.fxml"));
        fxmlLoader.setRoot(this);           // <--- Important for fx:root
        fxmlLoader.setController(this);      // <--- You are the controller
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setChatTitle(String title) {
        if (chatTitle != null) {
            chatTitle.setText(title);
        }
    }

    public String getChatTitle() {
        return chatTitle != null ? chatTitle.getText() : "";
    }
}
