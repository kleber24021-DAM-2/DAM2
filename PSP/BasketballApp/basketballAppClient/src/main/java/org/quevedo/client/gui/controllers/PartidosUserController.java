package org.quevedo.client.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.services.ServicePartidos;
import org.quevedo.sharedmodels.partido.Partido;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PartidosUserController {
    private final ServicePartidos servicePartidos;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    private MainScreenController mainController;
    @FXML
    private ListView<Partido> lvPartidos;
    @FXML
    private TextField txEquipo;
    @FXML
    private TextField txJornada;

    @Inject
    public PartidosUserController(ServicePartidos servicePartidos) {
        this.servicePartidos = servicePartidos;
    }


    @FXML
    private void realizarSearch() {
        String nombreEquipo = txEquipo.getText();
        AtomicReference<Integer> numJornada = new AtomicReference<>();
        numJornada.set(null);
        if (!txJornada.getText().isEmpty()) {
            numJornada.set(Integer.parseInt(txJornada.getText()));
        }

        Single<Either<String, List<Partido>>> single = Single.fromCallable(() -> servicePartidos.getFilteredPartidos(nombreEquipo, numJornada.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));

        single.subscribe(result -> result
                        .peek(listPartidos -> lvPartidos.getItems().setAll(listPartidos))
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
