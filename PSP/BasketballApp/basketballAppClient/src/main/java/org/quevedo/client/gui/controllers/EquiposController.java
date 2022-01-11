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
import org.quevedo.client.services.ServiceEquipos;
import org.quevedo.sharedmodels.Equipo;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.util.List;

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
        Single<Either<String, List<Equipo>>> single = Single.fromCallable(serviceEquipos::getAllEquipos)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        single.subscribe(result -> result
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
                    errorAlert.showAndWait();
                });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void registrarEquipo() {
        String nombreEquipo = txNombreEquipo.getText();

        Single<Either<String, Equipo>> single = Single.fromCallable(() -> serviceEquipos.registerEquipo(nombreEquipo))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> this.mainController
                        .getPantallaPrincipal().setCursor(Cursor.DEFAULT));
        single.subscribe(result -> result
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
                    errorAlert.showAndWait();
                });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
