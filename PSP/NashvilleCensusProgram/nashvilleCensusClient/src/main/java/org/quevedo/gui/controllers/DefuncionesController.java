package org.quevedo.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.quevedo.errors.ApiError;
import org.quevedo.gui.di.UserMessages;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.services.ServiceDefunciones;
import org.quevedo.services.ServicesPersonas;

import javax.inject.Inject;
import java.util.List;

public class DefuncionesController {
    private final ServiceDefunciones services;
    private final ServicesPersonas servicesPersonas;
    @FXML
    private ListView<Persona> lvPersonas;

    @Inject
    public DefuncionesController(ServiceDefunciones services, ServicesPersonas servicesPersonas) {
        this.services = services;
        this.servicesPersonas = servicesPersonas;
    }

    @FXML
    private void registrarDefuncion() {
        try {
            Persona toMorir = lvPersonas.getSelectionModel().getSelectedItem();
            int index = lvPersonas.getItems().indexOf(toMorir);
            var task = new Task<Either<ApiError, ServerResponse>>() {

                @Override
                protected Either<ApiError, ServerResponse> call() {
                    return services.registrarDefuncion(toMorir.getPersonalId());
                }
            };
            task.setOnSucceeded(event -> task.getValue()
                    .peek(response -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                        alert.showAndWait();
                        if (!response.isIncidence()){
                            lvPersonas.getItems().remove(index);
                        }
                    })
                    .peekLeft(apiError -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                        alert.showAndWait();
                    }));
            task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
            new Thread(task).start();
        }catch (NullPointerException nullPointerException){
            Alert alert = new Alert(Alert.AlertType.ERROR, UserMessages.SELECT_PERSONA);
            alert.showAndWait();
        }
    }

    public void setLista() {
        var task = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return servicesPersonas.getAllPersonas();
            }
        };
        task.setOnSucceeded(event -> task.getValue()
                .peek(personas -> lvPersonas.getItems().setAll(personas))
                .peekLeft(apiError -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                    alert.showAndWait();
                }));
        task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
        new Thread(task).start();
    }
}
