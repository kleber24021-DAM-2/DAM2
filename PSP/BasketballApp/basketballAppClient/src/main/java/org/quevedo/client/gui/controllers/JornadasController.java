package org.quevedo.client.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.services.ServiceJornada;
import org.quevedo.sharedmodels.Jornada;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

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
        Single<Either<String, Jornada>> single = Single.fromCallable(() -> serviceJornada.addJornada(selectedDate))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
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
                    alertError.showAndWait();
                });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void loadAllJornadas() {
        Single<Either<String, List<Jornada>>> single = Single.fromCallable(serviceJornada::getAllJornadas)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
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
                    alertError.showAndWait();
                });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
