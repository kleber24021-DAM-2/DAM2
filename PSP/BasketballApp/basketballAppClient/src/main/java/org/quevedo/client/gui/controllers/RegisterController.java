package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServicesUsuarios;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class RegisterController {
    private final ServicesUsuarios servicesUsuarios;
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextField txPassConf;
    @FXML
    private TextField txPass;
    @FXML
    private TextField txCorreo;
    private MainScreenController mainController;

    @Inject
    public RegisterController(ServicesUsuarios servicesUsuarios) {
        this.servicesUsuarios = servicesUsuarios;
    }

    @FXML
    private void doRegistry() {
        if (!txCorreo.getText().matches(UserMessages.MAIL_REGEX)) {
            alert.setContentText(UserMessages.MSG_VALID_MAIL);
            alert.showAndWait();
        } else if (txPass.getText().isEmpty()) {
            alert.setContentText(UserMessages.MSG_INTRODUCE_PASS);
            alert.showAndWait();
        } else if (!txPass.getText().equals(txPassConf.getText())) {
            alert.setContentText(UserMessages.MSG_NOT_MATCHING_PASS);
            alert.showAndWait();
        } else {
            UsuarioRegisterDTO usuarioRegister = new UsuarioRegisterDTO();
            usuarioRegister.setCorreo(txCorreo.getText());
            usuarioRegister.setPassword(txPass.getText());
            usuarioRegister.setPasswordConfirmation(txPassConf.getText());
            usuarioRegister.setTipoUsuario(TipoUsuario.NORMAL);

            servicesUsuarios.registrarUsuarioNormal(usuarioRegister)
                    .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(result -> result
                                    .peek(user -> {
                                        new Alert(Alert.AlertType.INFORMATION, UserMessages.MSG_INFO_EMAIL).showAndWait();
                                        mainController.showLogin();
                                    })
                                    .peekLeft(error -> {
                                        alert.setContentText(error);
                                        alert.showAndWait();
                                    }),
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    alert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                                } else {
                                    alert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                                }
                                log.error(throwable.getMessage(), throwable);
                                alert.showAndWait();
                            });
            mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
        }
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
