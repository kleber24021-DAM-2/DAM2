package org.quevedo.secretkeeper.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.quevedo.secretkeeper.dao.utils.DaoConsts;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;
import org.quevedo.secretkeeper.model.Secret;
import org.quevedo.secretkeeper.services.SecretService;
import org.quevedo.secretkeeper.services.UserService;

import javax.inject.Inject;

public class MainController {

    private final SecretService secretService;
    private final UserService userService;
    @FXML
    private TextField txSecretName;
    @FXML
    private ListView<String> lvUsers;
    @FXML
    private ListView<Secret> lvSecrets;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextArea txtFieldMessage;
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
        if (toDecrypt != null) {
            secretService.fetchSecret(toDecrypt)
                    .peek(secret -> {
                        txtFieldMessage.setText(secret.getMessage());
                        txSecretName.setText(secret.getSecretName());
                    })
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        } else {
            new Alert(Alert.AlertType.ERROR, GuiConsts.MSG_SELECT_PASSWORD);
        }
    }

    @FXML
    private void cipherSecret() {
        String message = txtFieldMessage.getText();
        String password = txtPassword.getText();
        String passwordName = txSecretName.getText();
        if (!passwordName.isBlank() && !message.isBlank() && !password.isBlank()) {
            Secret toEncrypt = new Secret();
            toEncrypt.setMessage(message);
            toEncrypt.setSecretName(passwordName);
            lvUsers.getSelectionModel()
                    .getSelectedItems()
                    .forEach(user -> toEncrypt.addToSharedUsers(user, DaoConsts.EMPTY_STRING));
            secretService.saveSecret(toEncrypt)
                    .peek(secret -> lvSecrets.getItems().add(secret))
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        } else {
            new Alert(Alert.AlertType.ERROR, GuiConsts.MSG_INSERT_ENCRYPT);
        }
    }

    @FXML
    private void loadAllSecrets() {
        lvUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        secretService.getAllSecrets()
                .peek(secrets -> lvSecrets.getItems().setAll(secrets))
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        userService.getAllUsers()
                .peek(strings -> lvUsers.getItems().setAll(strings))
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }

    @FXML
    private void doLogin() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.doLogin(usuario, password)
                    .peek(isLogged -> {
                        if (Boolean.TRUE.equals(isLogged)) {
                            new Alert(Alert.AlertType.INFORMATION, GuiConsts.MSG_USER_LOGGED).showAndWait();
                        } else {
                            new Alert(Alert.AlertType.WARNING, GuiConsts.MSG_USER_NOT_LOGGED).showAndWait();
                        }
                    })
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }
    }

    @FXML
    public void doRegister() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.createUser(usuario, password)
                    .peek(isRegistered -> {
                        if (Boolean.TRUE.equals(isRegistered)) {
                            new Alert(Alert.AlertType.INFORMATION, GuiConsts.MSG_USER_REGISTERED).showAndWait();
                        } else {
                            new Alert(Alert.AlertType.WARNING, GuiConsts.MSG_USER_NOT_REGISTERED).showAndWait();
                        }
                    })
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }
    }
}
