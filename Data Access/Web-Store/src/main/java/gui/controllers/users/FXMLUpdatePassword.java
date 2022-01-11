package gui.controllers.users;

import gui.controllers.FXMLPrincipalController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import model.user.FullUser;
import services.UserService;

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
        UserService userService = new UserService();
        User previousUser = new User(parent.getLoggedUser().getUserId(), parent.getLoggedUser().getUsername(), previousePassword.getText());
        if (userService.checkUserPassword(previousUser) != null){
            User updatedUser = new User(parent.getLoggedUser().getUserId(), parent.getLoggedUser().getUsername(), newPassword.getText());
            userService.updateUser(updatedUser);
            errorBox.setText("Password updated");
        }else {
            errorBox.setText("The actual password doesnt match");
        }
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
