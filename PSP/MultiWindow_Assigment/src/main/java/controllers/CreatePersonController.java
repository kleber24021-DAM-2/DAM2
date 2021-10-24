package controllers;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import models.Person;
import services.ServicesPerson;
import utils.Strings;

public class CreatePersonController {
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    private final ServicesPerson servicesPerson = new ServicesPerson();
    @FXML
    private MFXTextField textName;
    @FXML
    private MFXTextField textSurname;
    @FXML
    private MFXTextField textAge;
    @FXML
    private ToggleGroup personSexo;
    @FXML
    private MFXDatePicker datePickerBirth;

    public void createPerson(ActionEvent actionEvent) {
        Person p = new Person();
        try {
            p.setName(textName.getText());
            p.setSurname(textSurname.getText());
            p.setAge(Integer.parseInt(textAge.getText()));
            RadioButton selectedSexo = (RadioButton) personSexo.getSelectedToggle();
            p.setMale(selectedSexo.getText().equals("Hombre"));
            p.setBirthDate(datePickerBirth.getDate());
            if (servicesPerson.addPerson(p)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText(Strings.MESSAGE_ADDED_PERSON);
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText(Strings.WARNING_INCOMPLETE_DATA);
            }
        } catch (NumberFormatException numEx) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(Strings.WARNING_CHECK_AGE);
        } catch (NullPointerException nullEx) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(Strings.ERROR_UNEXPECTED);
        }
        alert.showAndWait();
    }
}
