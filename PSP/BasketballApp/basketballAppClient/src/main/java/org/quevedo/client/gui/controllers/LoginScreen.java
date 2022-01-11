package org.quevedo.client.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.services.ServicesUsuarios;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import retrofit2.HttpException;

import javax.inject.Inject;

public class LoginScreen {

    private final ServicesUsuarios servicesUsuarios;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private TextField txCorreo;
    @FXML
    private PasswordField txPass;

    @Inject
    public LoginScreen(ServicesUsuarios servicesUsuarios) {
        this.servicesUsuarios = servicesUsuarios;
    }

    public void doLogin() {
        String correo = txCorreo.getText();
        String password = txPass.getText();

        Single<Either<String, LoginResultDTO>> single = Single.fromCallable(() ->
                        servicesUsuarios.doLogin(correo, password))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        single.subscribe(result -> result
                        .peek(loginResultDTO -> {
                            if (loginResultDTO.isLogged()) {
                                mainController.doLogin(loginResultDTO);
                            } else {
                                alertError.setContentText(loginResultDTO.getMessage());
                                alertError.showAndWait();
                            }
                        })
                        .peekLeft(error -> {
                            alertError.setContentText(error);
                            alertError.showAndWait();
                        }),
                throwable -> {
                    if (throwable instanceof HttpException) {
                        alertError.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                    } else {
                        alertError.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                    }
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
            Single<Either<String, Void>> singlePasswordChange = Single
                    .fromCallable(() -> servicesUsuarios.changePassword(txCorreo.getText()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT));
            singlePasswordChange.subscribe(result -> result
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
                        alertError.showAndWait();
                    });
            mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
        }
    }
}
