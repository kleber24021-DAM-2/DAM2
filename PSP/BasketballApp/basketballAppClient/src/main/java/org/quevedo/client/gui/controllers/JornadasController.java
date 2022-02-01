package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServiceJornada;
import org.quevedo.sharedmodels.Jornada;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.time.LocalDate;

@Log4j2
public class JornadasController {
    private final ServiceJornada serviceJornada;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private ListView<Jornada> lvJornadas;
    @FXML
    private DatePicker dateSelector;

    @Inject
    public JornadasController(ServiceJornada serviceJornada) {
        this.serviceJornada = serviceJornada;
    }

    @FXML
    private void registrarJornada() {
        LocalDate selectedDate = dateSelector.getValue();

        serviceJornada.addJornada(selectedDate)
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(jornada -> lvJornadas.getItems().add(jornada))
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
                            log.error(throwable.getMessage(), throwable);
                            alertError.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void loadAllJornadas() {

        serviceJornada.getAllJornadas()
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(jornadaList -> lvJornadas.getItems().setAll(jornadaList))
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
                            log.error(throwable.getMessage(), throwable);
                            alertError.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
