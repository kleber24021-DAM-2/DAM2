package controllers;

import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Person;
import services.ServicesPerson;
import utils.Strings;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeletePersonController implements ChildController, Initializable {
    private final ServicesPerson service = new ServicesPerson();

    @FXML
    private MFXListView<Person> listViewPerson;
    private MainController msc;

    @Override
    public void setParent(MainController msc) {
        this.msc = msc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void showData() {
        listViewPerson.getItems().clear();
        listViewPerson.getItems().addAll(service.getPersonList());
    }

    @FXML
    private void deletePerson() {
        Person selectedPerson = listViewPerson.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Strings.CONFIRMATION_DELETE);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (service.deletePerson(selectedPerson)) {
                    listViewPerson.getItems().remove(selectedPerson);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(Strings.WARNING_SELECT_PERSON);
            alert.showAndWait();
        }
    }
}
