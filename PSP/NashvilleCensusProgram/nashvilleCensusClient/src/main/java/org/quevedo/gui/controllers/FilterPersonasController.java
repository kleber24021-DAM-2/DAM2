package org.quevedo.gui.controllers;

import io.vavr.control.Either;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.quevedo.errors.ApiError;
import org.quevedo.gui.di.UserMessages;
import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.persona.Sexo;
import org.quevedo.services.ServicesPersonas;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterPersonasController implements Initializable {

    @FXML
    private TextField tfBirthYear;
    @FXML
    private ListView<Persona> lvPersonas;
    @FXML
    private ComboBox<EstadoCivil> cbEstadoCivil;
    @FXML
    private TextField tfProcedencia;
    @FXML
    private TextField tfNHijos;
    @FXML
    private ServicesPersonas services;

    @Inject
    public FilterPersonasController(ServicesPersonas services){
        this.services = services;
    }

    @FXML
    private void filtrarPersonas() {
        var task = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                String birthYear;
                if (tfBirthYear.getText().isEmpty()){
                    birthYear = null;
                }else {
                    birthYear = tfBirthYear.getText();
                }
                String hijos;
                if (tfNHijos.getText().isEmpty()){
                    hijos = null;
                }else {
                    hijos = tfNHijos.getText();
                }
                return services.filterPersonas(cbEstadoCivil.getSelectionModel().getSelectedItem(),
                        birthYear,
                        tfProcedencia.getText(),
                        hijos);
            }
        };
        task.setOnSucceeded(event -> task.getValue()
                .peek(personas -> {
                    if (personas.isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.WARNING, UserMessages.NO_RESULT);
                        alert.showAndWait();
                    }
                    lvPersonas.getItems().setAll(personas);
                })
                .peekLeft(apiError -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, apiError.getUserMessage());
                    alert.showAndWait();
                }));
        task.setOnFailed(event -> new Alert(Alert.AlertType.ERROR, UserMessages.UNEXPECTED_ERROR).showAndWait());
        new Thread(task).start();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbEstadoCivil.getItems().setAll(EstadoCivil.values());

        tfBirthYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfBirthYear.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        tfNHijos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNHijos.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
