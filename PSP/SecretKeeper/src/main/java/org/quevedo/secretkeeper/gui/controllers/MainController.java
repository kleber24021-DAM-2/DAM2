package org.quevedo.secretkeeper.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;
import org.quevedo.secretkeeper.model.Secret;
import org.quevedo.secretkeeper.services.SecretService;
import org.quevedo.secretkeeper.services.UserService;

import javax.inject.Inject;

public class MainController {

    private final SecretService secretService;
    private final UserService userService;
    @FXML
    private ListView<Secret> lvSecrets;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextArea txtFieldMessage;
    @FXML
    private TextField txtOwner;
    @FXML
    private TextField txUsuario;



    @Inject
    public MainController(SecretService secretService,
                          UserService userService) {
        this.secretService = secretService;
        this.userService = userService;
    }

    @FXML
    private void decryptSecret() {
        Secret toDecrypt = lvSecrets.getSelectionModel().getSelectedItem();
        String password = txtPassword.getText();
        if (!password.isBlank() && toDecrypt != null) {
            secretService.fetchSecret(toDecrypt, password)
                    .peek(secret -> txtFieldMessage.setText(secret.getMessage()))
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        } else {
            new Alert(Alert.AlertType.ERROR, GuiConsts.MSG_SELECT_PASSWORD);
        }
    }

    @FXML
    private void cipherSecret() {
        String owner = txtOwner.getText();
        String message = txtFieldMessage.getText();
        String password = txtPassword.getText();
        if (!owner.isBlank() && !message.isBlank() && !password.isBlank()) {
            Secret toEncrypt = new Secret();
            toEncrypt.setOwner(owner);
            toEncrypt.setMessage(message);

            secretService.saveSecret(toEncrypt, password)
                    .peek(secret -> lvSecrets.getItems().add(secret))
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        } else {
            new Alert(Alert.AlertType.ERROR, GuiConsts.MSG_INSERT_ENCRYPT);
        }
    }

    @FXML
    private void loadAllSecrets() {
        secretService.getAllSecrets()
                .peek(secrets -> lvSecrets.getItems().setAll(secrets))
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }

    @FXML
    private void doLogin() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.doLogin(usuario, password)
                    .peek(msg -> new Alert(Alert.AlertType.INFORMATION, msg).showAndWait())
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }
    }

    @FXML
    public void doRegister() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.createUser(usuario, password)
                    .peek(msg -> new Alert(Alert.AlertType.INFORMATION, msg).showAndWait())
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }
    }
}
