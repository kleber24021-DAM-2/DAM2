package org.quevedo.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.services.ServiceEquipos;
import org.quevedo.client.services.ServiceJornada;
import org.quevedo.client.services.ServicePartidos;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.Jornada;
import org.quevedo.sharedmodels.partido.Partido;
import retrofit2.HttpException;

import javax.inject.Inject;

@Log4j2
public class PartidosUserController {
    private final ServicePartidos servicePartidos;
    private final ServiceEquipos serviceEquipos;
    private final ServiceJornada serviceJornada;
    private final Alert alertError = new Alert(Alert.AlertType.ERROR);
    public ComboBox<Equipo> cbEquipo;
    public ComboBox<Jornada> cbJornada;
    private MainScreenController mainController;
    @FXML
    private ListView<Partido> lvPartidos;

    @Inject
    public PartidosUserController(ServicePartidos servicePartidos,
                                  ServiceJornada serviceJornada,
                                  ServiceEquipos serviceEquipos) {
        this.servicePartidos = servicePartidos;
        this.serviceEquipos = serviceEquipos;
        this.serviceJornada = serviceJornada;
    }


    @FXML
    private void realizarSearch() {
        String nombreEquipo;
        Integer numJornada;
        //En estos momentos echo de menos los ?: de Kotlin
        try {
            nombreEquipo = cbEquipo.getSelectionModel().getSelectedItem().getNombre();
        } catch (NullPointerException nullPointerException) {
            nombreEquipo = null;
        }
        try {
            numJornada = cbJornada.getSelectionModel().getSelectedItem().getIdJornada();
        } catch (NullPointerException nullPointerException) {
            numJornada = null;
        }
        servicePartidos.getFilteredPartidos(nombreEquipo, numJornada)
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
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
                            log.error(throwable.getMessage(), throwable);
                            alertError.showAndWait();
                        });
        mainController.getPantallaPrincipal().setCursor(Cursor.WAIT);
    }

    public void chargeAllLists() {
        servicePartidos.getAllPartidos()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
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
                            log.error(throwable.getMessage(), throwable);
                            alertError.showAndWait();
                        });
        serviceJornada.getAllJornadas()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(listJornada -> cbJornada.getItems().setAll(listJornada))
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
        serviceEquipos.getAllEquipos()
                .doFinally(() -> mainController.getPantallaPrincipal().setCursor(Cursor.DEFAULT))
                .subscribe(result -> result
                                .peek(listEquipos -> cbEquipo.getItems().setAll(listEquipos))
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
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }
}
