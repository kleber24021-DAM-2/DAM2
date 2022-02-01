package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServiceEquipos;
import org.quevedo.sharedmodels.Equipo;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class EquiposController {
    private final ServiceEquipos serviceEquipos;
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private ListView<Equipo> lvEquipos;
    @FXML
    private TextField txNombreEquipo;

    @Inject
    public EquiposController(ServiceEquipos serviceEquipos) {
        this.serviceEquipos = serviceEquipos;
    }

    public void loadLista() {

        serviceEquipos.getAllEquipos()
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(equipoList -> lvEquipos.getItems().setAll(equipoList))
                                .peekLeft(mensaje -> {
                                    errorAlert.setContentText(mensaje);
                                    errorAlert.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                errorAlert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                errorAlert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            errorAlert.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void registrarEquipo() {
        String nombreEquipo = txNombreEquipo.getText();

        serviceEquipos.registerEquipo(nombreEquipo)
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(equipo -> lvEquipos.getItems().add(equipo))
                                .peekLeft(mensaje -> {
                                    errorAlert.setContentText(mensaje);
                                    errorAlert.showAndWait();
                                }),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                errorAlert.setContentText(UserMessages.MSG_DB_NO_CONNECTION);
                            } else {
                                errorAlert.setContentText(UserMessages.MSG_UNEXPECTED_ERROR);
                            }
                            log.error(throwable.getMessage(), throwable);
                            errorAlert.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
