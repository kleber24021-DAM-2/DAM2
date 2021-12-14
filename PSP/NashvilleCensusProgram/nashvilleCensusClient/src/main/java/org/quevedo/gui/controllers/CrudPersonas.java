package org.quevedo.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import org.quevedo.errors.ApiError;
import org.quevedo.gui.di.UserMessages;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.services.ServicesPersonas;
import javafx.fxml.FXML;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.persona.Sexo;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class CrudPersonas {
    private final ServicesPersonas services;
    @FXML
    private ListView<Persona> lvPersonas;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<EstadoCivil> cbEstadoCivil;
    @FXML
    private ComboBox<Sexo> cbSexo;
    @FXML
    private TextField tfProcedencia;
    @FXML
    private DatePicker dateNacimiento;
    @Inject
    public CrudPersonas(ServicesPersonas services) {
        this.services = services;
    }

    @FXML
    private void createPersona() {
        Persona newPersona = new Persona(tfNombre.getText(), "0", EstadoCivil.SOLTERO, cbSexo.getValue(), dateNacimiento.getValue(), tfProcedencia.getText(), null, Collections.emptyList());
        var task = new Task<Either<ApiError, Persona>>() {

            @Override
            protected Either<ApiError, Persona> call() {
                return services.createPersona(newPersona);
            }
        };
        task.setOnSucceeded(event -> task.getValue()
                .peek(persona -> lvPersonas.getItems().add(persona))
                .peekLeft(apiError -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                    alert.showAndWait();
                }));
        task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
        new Thread(task).start();
    }

    @FXML
    private void updatePersona() {
        Persona newPersona;
        int index;
        try {
            Persona selectedPersona = lvPersonas.getSelectionModel().getSelectedItem();
           newPersona = new Persona(tfNombre.getText(), selectedPersona.getPersonalId(), cbEstadoCivil.getValue(), cbSexo.getValue(), dateNacimiento.getValue(), tfProcedencia.getText(), selectedPersona.getIdPersonaCasada(), selectedPersona.getHijos());
           index = lvPersonas.getItems().indexOf(selectedPersona);
            var task = new Task<Either<ApiError, Persona>>() {

                @Override
                protected Either<ApiError, Persona> call() {
                    return services.createPersona(newPersona);
                }
            };
            task.setOnSucceeded(event -> task.getValue()
                    .peek(persona -> {
                        lvPersonas.getItems().remove(index);
                        lvPersonas.getItems().add(index, persona);
                    })
                    .peekLeft(apiError -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                        alert.showAndWait();
                    }));
            task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
            new Thread(task).start();
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR, UserMessages.SELECT_PERSONA);
            alert.showAndWait();
        }

    }

    @FXML
    private void erasePersona() {
        Persona selectedPersona;
        int index;
        try {
            selectedPersona = lvPersonas.getSelectionModel().getSelectedItem();
            index = lvPersonas.getItems().indexOf(selectedPersona);

            var task = new Task<Either<ApiError, ServerResponse>>() {

                @Override
                protected Either<ApiError, ServerResponse> call() {
                    return services.mudarPersona(selectedPersona.getPersonalId());
                }
            };
            task.setOnSucceeded(event -> task.getValue()
                    .peek(response -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                        alert.showAndWait();
                        lvPersonas.getItems().remove(index);
                    })
                    .peekLeft(apiError -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                        alert.showAndWait();
                    }));
            task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
            new Thread(task).start();
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR, UserMessages.SELECT_PERSONA);
            alert.showAndWait();
        }

    }

    public void setLista(){
        var task = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return services.getAllPersonas();
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

        cbEstadoCivil.getItems().setAll(EstadoCivil.values());
        cbSexo.getItems().setAll(Sexo.values());
    }
}
