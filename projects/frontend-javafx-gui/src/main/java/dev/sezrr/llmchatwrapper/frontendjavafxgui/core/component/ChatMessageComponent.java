package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.component;

import com.sandec.mdfx.MarkdownView;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.enums.MessageSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;

public class ChatMessageComponent extends HBox {
    @FXML private Label modelName;
    @FXML private FontIcon avatarIcon;
    @FXML private VBox bubbleContainer;
    @FXML private StackPane markdownHolder;    // placeholder for MarkdownView
    @FXML private HBox modelContainer;

    // Buffer all incoming tokens
    private final StringBuilder fullTextBuffer = new StringBuilder();
    // The MDFX MarkdownView control
    private final MarkdownView markdownView = new MarkdownView("");

    public ChatMessageComponent(String initialMessage,
                                String model,
                                MessageSender sender) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/message/ChatMessage.fxml"
                )
        );
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ChatMessageComponent.fxml", e);
        }

        // 1) Add the MarkdownView into the placeholder
        markdownHolder.getChildren().add(markdownView);
        // 2) Load default CSS for mdfx
        markdownView.getStylesheets().add("/com/sandec/mdfx/mdfx-default.css");
        // 3) Initialize with the first chunk
        appendMessageText(initialMessage);

        // Style per sender
        switch (sender) {
            case ASSISTANT -> {
                getStyleClass().add("assistant-message");
                modelName.setText(model);
                avatarIcon.setVisible(true);
                modelName.setVisible(true);
            }
            case USER -> {
                getStyleClass().add("user-message");
                avatarIcon.setVisible(false);
                bubbleContainer.getChildren().remove(modelContainer);
            }
        }

        // Load your own bubble CSS
        URL cssUrl = getClass().getResource(
                "/dev/sezrr/llmchatwrapper/frontendjavafxgui/components/message/chat-message.css"
        );
        if (cssUrl != null) {
            getStylesheets().add(cssUrl.toExternalForm());
        }
    }

    public void appendMessageText(String token) {
//        fullTextBuffer.append(token).append(" ");
        fullTextBuffer.append(token);
        markdownView.setMdString(fullTextBuffer.toString());
    }

    public void finalizeMessage() {
        String finalText = fullTextBuffer.toString();
        markdownView.setMdString(finalText); // Update the content

        markdownHolder.getChildren().remove(markdownView); // Remove to trigger re-render
        markdownView.setVisible(false);                    // Force JavaFX to treat it as updated
        markdownView.applyCss();                           // Force style re-evaluation
        markdownView.layout();                             // Force layout pass
        markdownView.setVisible(true);
        markdownHolder.getChildren().add(markdownView);    // Add again

        markdownHolder.requestLayout();                    // Extra layout hint for parent
    }
}
