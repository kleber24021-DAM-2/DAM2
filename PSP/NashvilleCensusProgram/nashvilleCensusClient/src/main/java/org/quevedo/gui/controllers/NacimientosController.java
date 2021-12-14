package org.quevedo.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.quevedo.errors.ApiError;
import org.quevedo.gui.di.UserMessages;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.persona.Sexo;
import org.quevedo.model.serverresponses.ServerResponse;
import org.quevedo.services.ServiceNacimientos;
import org.quevedo.services.ServicesPersonas;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NacimientosController {
    private final ServiceNacimientos services;
    @FXML
    private ListView<Persona> lvPersonas1;
    @FXML
    private ListView<Persona> lvPersonas2;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<Sexo> cbSexo;
    @FXML
    private DatePicker dateNacimiento;

    private ServicesPersonas servicesPersonas;
    @Inject
    public NacimientosController(ServiceNacimientos services, ServicesPersonas servicesPersonas) {
        this.services = services;
        this.servicesPersonas = servicesPersonas;
    }

    @FXML
    private void registrarNacimiento() {
        try {
            String idProgenitor1 = lvPersonas1.getSelectionModel().getSelectedItem().getPersonalId();
            String idProgenitor2 = lvPersonas2.getSelectionModel().getSelectedItem().getPersonalId();
            Persona hijo = new Persona(tfNombre.getText(), "0", EstadoCivil.SOLTERO, cbSexo.getSelectionModel().getSelectedItem(), dateNacimiento.getValue(),
                    "Nashville", null, Collections.emptyList());

            var task = new Task<Either<ApiError, ServerResponse>>() {

                @Override
                protected Either<ApiError, ServerResponse> call() {
                    return services.registrarNacimiento(idProgenitor1, idProgenitor2, hijo);
                }
            };
            task.setOnSucceeded(event -> task.getValue()
                    .peek(response -> {
                        if (!response.isIncidence()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                            setLista();
                            alert.showAndWait();
                        }else {
                            notMarriedParents(idProgenitor1, idProgenitor2);
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

    private void notMarriedParents(String idPersona1, String idPersona2){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, UserMessages.VERIFY_INFO);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent() && answer.get().equals(ButtonType.OK)){
            servicesPersonas.mudarPersona(idPersona1);
            servicesPersonas.mudarPersona(idPersona2);
            new Alert(Alert.AlertType.INFORMATION, UserMessages.EXILED_PEOPLE).showAndWait();
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
                    lvPersonas2.getItems().setAll(personas);
                })
                .peekLeft(apiError -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                    alert.showAndWait();
                }));
        task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
        new Thread(task).start();

        cbSexo.getItems().setAll(Sexo.values());
    }

}
