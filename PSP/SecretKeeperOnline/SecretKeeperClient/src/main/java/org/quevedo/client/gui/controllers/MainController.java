package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.client.gui.utils.GuiConsts;
import org.quevedo.client.services.ServiceSecretos;
import org.quevedo.client.services.ServiceUsuario;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private final ServiceSecretos secretService;
    private final ServiceUsuario userService;
    private final CacheAuth cacheAuth;

    @FXML
    private ListView<Secreto> lvSecrets;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextArea txtFieldMessage;
    @FXML
    private TextField txUsuario;
    @FXML
    private ListView<Usuario> lvUsers;
    @FXML
    private TextField txSecretName;

    @Inject
    public MainController(ServiceSecretos secretService,
                          ServiceUsuario userService,
                          CacheAuth cacheAuth) {
        this.secretService = secretService;
        this.userService = userService;
        this.cacheAuth = cacheAuth;
    }

    @FXML
    private void decryptSecret() {
        secretService.decryptSecret(lvSecrets.getSelectionModel().getSelectedItem())
                .peek(secreto -> {
                    txtFieldMessage.setText(secreto.getMessage());
                    txSecretName.setText(secreto.getSecretName());
                })
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }

    @FXML
    private void cipherSecret() {
        Secreto toSaveSecret = new Secreto();
        toSaveSecret.setSecretOwner(cacheAuth.getLoggedUsername());
        toSaveSecret.setSecretName(txSecretName.getText());
        toSaveSecret.setMessage(txtFieldMessage.getText());
        Map<Usuario, String> sharedWith = new HashMap<>();
        lvUsers.getSelectionModel().getSelectedItems()
                .forEach(usuario -> sharedWith.put(usuario, ""));
        toSaveSecret.setSharedWith(sharedWith);
        secretService.saveSecreto(toSaveSecret)
                .peek(secreto -> new Alert(Alert.AlertType.INFORMATION, secreto.toString()).showAndWait())
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }

    @FXML
    private void loadAllLists() {
        lvUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userService.getAllUsuarios()
                .peek(users -> lvUsers.getItems().setAll(users))
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        secretService.getAllSecretos()
                .peek(secretos -> lvSecrets.getItems().setAll(secretos))
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }


    @FXML
    private void doLogin() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.doLogin(usuario, password)
                    .peek(user -> new Alert(Alert.AlertType.INFORMATION, GuiConsts.MSG_USER_LOGGED + "\n" + user.toString()).showAndWait())
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }

    }

    @FXML
    private void doRegister() {
        String usuario = txUsuario.getText();
        String password = txtPassword.getText();
        if (!usuario.isBlank() && !password.isBlank()) {
            userService.createUser(usuario, password)
                    .peek(user ->
                            new Alert(Alert.AlertType.INFORMATION, GuiConsts.MSG_USER_REGISTERED + user.toString()).showAndWait()
                    )
                    .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
        }
    }
}
