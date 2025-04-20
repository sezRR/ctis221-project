package dev.sezrr.examples.javafx_googleoauth2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {

    @FXML private Label  welcomeText;
    @FXML private Button loginButton;

    @FXML private void onHelloButtonClick() {
        welcomeText.setText("Hello World!");
    }

    /** Called by HelloApplication once OAuth succeeds */
    public void setAuthSuccess(String msg) {
        welcomeText.setText(msg);
        loginButton.setDisable(true);
    }

    @FXML private void onLoginButtonClick() {
        // optional: you could expose a callback in HelloApplication
    }
}
