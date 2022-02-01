package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.dao.utils.StringConstants;
import org.quevedo.client.services.ServiceLogout;
import org.quevedo.client.services.ServicesUsuarios;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class LoginScreen {

    private final ServicesUsuarios servicesUsuarios;
    private final ServiceLogout serviceLogout;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private TextField txCorreo;
    @FXML
    private PasswordField txPass;

    @Inject
    public LoginScreen(ServicesUsuarios servicesUsuarios, ServiceLogout serviceLogout) {
        this.servicesUsuarios = servicesUsuarios;
        this.serviceLogout = serviceLogout;
    }

    public void doLogin() {
        String correo = txCorreo.getText();
        String password = txPass.getText();

        servicesUsuarios.doLogin(correo, password)
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(loginResultDTO -> {
                                    mainController.doLogin(loginResultDTO);
                                })
                                .peekLeft(error -> {
                                    alertError.setContentText(error);
                                    serviceLogout.doLogout();
                                    alertError.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                if (((HttpException) throwable).code() == 401) {
                                    alertError.setContentText(StringConstants.INCORRECT_PASSWORD);
                                }
                            } else {
                                alertError.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            serviceLogout.doLogout();
                            alertError.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void showRegistrar() {
        mainController.showRegister();
    }

    public void startPasswordChange() {
        if (txCorreo.getText().isEmpty()) {
            alertError.setContentText(UserMessages.MSG_INSERT_MAIL);
            alertError.showAndWait();
        } else {

            servicesUsuarios.changePassword(txCorreo.getText())
                    .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                    .subscribe(result -> result
                                    .peek(resultString -> new Alert(Alert.AlertType.INFORMATION, UserMessages.MSG_INFO_EMAIL).showAndWait())
                                    .peekLeft(errorMessage -> {
                                        alertError.setContentText(errorMessage);
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
}
