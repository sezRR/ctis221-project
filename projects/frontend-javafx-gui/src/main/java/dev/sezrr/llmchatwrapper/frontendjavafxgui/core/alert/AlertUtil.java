package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert;

import javafx.scene.control.Alert;

public class AlertUtil {
    private static void setAlertProperties(Alert alert, String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
    }
    
    public static void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setAlertProperties(alert, "Error", header, content);
        alert.showAndWait();
    }

    public static void showSuccess(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        setAlertProperties(alert, "Success", header, content);
        alert.showAndWait();
    }
    
    public static void showWarning(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        setAlertProperties(alert, "Warning", header, content);
        alert.showAndWait();
    }
    
    public static void showInfo(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        setAlertProperties(alert, "Info", header, content);
        alert.showAndWait();
    }
}
