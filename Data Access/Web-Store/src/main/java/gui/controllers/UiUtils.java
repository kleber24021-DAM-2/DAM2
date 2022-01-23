package gui.controllers;

import javafx.scene.control.Alert;

public class UiUtils {
    public static void showAlert(String message, Alert.AlertType alertType){
        new Alert(alertType, message).showAndWait();
    }
}
