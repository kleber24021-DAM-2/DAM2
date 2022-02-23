package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.quevedo.client.gui.utils.GuiConsts;
import org.quevedo.client.services.ServiceUsuario;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;

public class MainController {
    //    private final SecretService secretService;
    private final ServiceUsuario userService;
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
    public MainController(/*ServiceSecretoz secretService,*/
            ServiceUsuario userService) {
//        this.secretService = secretService;
        this.userService = userService;
    }

    @FXML
    private void decryptSecret() {
        // TODO: 23/02/2022
    }

    @FXML
    private void cipherSecret() {
        // TODO: 23/02/2022
    }

    @FXML
    private void loadAllSecrets() {
        lvUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userService.getAllUsuarios()
                .peek(users -> lvUsers.getItems().setAll(users))
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
