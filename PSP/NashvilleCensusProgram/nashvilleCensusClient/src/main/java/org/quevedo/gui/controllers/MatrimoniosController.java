package org.quevedo.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.quevedo.errors.ApiError;
import org.quevedo.gui.di.UserMessages;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.persona.Sexo;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.services.ServiceMatrimonios;
import org.quevedo.services.ServicesPersonas;

import javax.inject.Inject;
import java.util.List;

public class MatrimoniosController {
    @FXML
    private ListView<Persona> lvPersonas2;
    @FXML
    private ListView<Persona> lvPersonas1;

    private ServiceMatrimonios services;
    private ServicesPersonas servicesPersonas;

    @Inject
    public MatrimoniosController(ServiceMatrimonios services, ServicesPersonas servicesPersonas){
        this.services = services;
        this.servicesPersonas = servicesPersonas;
    }

    @FXML
    private void registrarMatrimonio() {
        try {
            Persona conyuge1 = lvPersonas1.getSelectionModel().getSelectedItem();
            Persona conyuge2 = lvPersonas2.getSelectionModel().getSelectedItem();

            var task = new Task<Either<ApiError, ServerResponse>>() {

                @Override
                protected Either<ApiError, ServerResponse> call() {
                    return services.registrarMatrimonio(conyuge1.getPersonalId(), conyuge2.getPersonalId());
                }
            };
            task.setOnSucceeded(event -> task.getValue()
                    .peek(response -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                        alert.showAndWait();
                        setLista();
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

    public void setLista(){
        var task = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return servicesPersonas.getAllPersonas();
            }
        };
        task.setOnSucceeded(event -> task.getValue()
                .peek(personas -> {
                    lvPersonas1.getItems().setAll(personas);
                    lvPersonas2.getItems().setAll((personas));
                })
                .peekLeft(apiError -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                    alert.showAndWait();
                }));
        task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
        new Thread(task).start();

    }
}
