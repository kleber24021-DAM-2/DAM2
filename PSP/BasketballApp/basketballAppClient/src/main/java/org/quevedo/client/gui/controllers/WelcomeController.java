package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {
    @FXML
    private Label usernameTag;

    public void setUserName(String userName) {
        usernameTag.setText(userName);
    }
}
