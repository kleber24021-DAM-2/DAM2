package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServicesUsuarios;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class UsuariosController {
    private final ServicesUsuarios servicesUsuarios;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private ListView<UsuarioGetDTO> lvUsers;
    @FXML
    private TextField txCorreo;
    @FXML
    private PasswordField txPassword;
    @FXML
    private PasswordField txConfirmationPass;
    @FXML
    private ComboBox<TipoUsuario> cbUserType;

    @Inject
    public UsuariosController(ServicesUsuarios servicesUsuarios) {
        this.servicesUsuarios = servicesUsuarios;
    }

    @FXML
    private void addUsuario() {
        if (txCorreo.getText().isEmpty() || txPassword.getText().isEmpty() || txConfirmationPass.getText().isEmpty() || cbUserType.getSelectionModel().isEmpty()) {
            alertError.setContentText(UserMessages.MSG_NO_EMPTY_FIELD);
            alertError.showAndWait();
        } else if (!txPassword.getText().equals(txConfirmationPass.getText())) {
            alertError.setContentText(UserMessages.MSG_NOT_MATCHING_PASS);
            alertError.showAndWait();
        } else {
            UsuarioRegisterDTO userToRegister = new UsuarioRegisterDTO();
            userToRegister.setCorreo(txCorreo.getText());
            userToRegister.setPassword(txPassword.getText());
            userToRegister.setPasswordConfirmation(txPassword.getText());
            userToRegister.setTipoUsuario(cbUserType.getSelectionModel().getSelectedItem());

            servicesUsuarios.registrarUsuarioAdmin(userToRegister)
                    .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(result -> result
                                    .peek(user -> {
                                        lvUsers.getItems().add(user);
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION, UserMessages.MSG_EMAIL_SENDED);
                                        alert.showAndWait();
                                    })
                                    .peekLeft(errorMensaje -> {
                                        alertError.setContentText(errorMensaje);
                                        alertError.showAndWait();
                                    }),
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    alertError.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                                } else {
                                    alertError.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                                }
                                log.error(throwable.getMessage(), throwable);
                                alertError.showAndWait();
                            });
            mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
        }
    }

    public void loadUsersList() {

        servicesUsuarios.getAllUsers()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(userList -> lvUsers.getItems().setAll(userList))
                                .peekLeft(errorMensaje -> {
                                    alertError.setContentText(errorMensaje);
                                    alertError.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                alertError.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                alertError.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            alertError.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);

        cbUserType.getItems().setAll(TipoUsuario.values());
    }


    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
