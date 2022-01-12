package gui.controllers.users;

import gui.controllers.FXMLPrincipalController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLUpdatePassword {

    @FXML
    private TextField previousePassword;
    @FXML
    private TextField newPassword;
    @FXML
    private Label errorBox;

    private FXMLPrincipalController parent;

    @FXML
    private void updatePassword() {
        //TODO
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
