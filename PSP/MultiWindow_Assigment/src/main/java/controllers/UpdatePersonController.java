package controllers;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import models.Person;
import services.ServicesPerson;
import utils.Strings;

public class UpdatePersonController implements ChildController {

    @FXML
    private MFXRadioButton radioMale;
    @FXML
    private MFXRadioButton radioFemale;
    @FXML
    private MFXListView<Person> personListView;
    @FXML
    private MFXTextField textName;
    @FXML
    private MFXTextField textSurname;
    @FXML
    private MFXTextField textAge;
    @FXML
    private ToggleGroup toggleSexo;
    @FXML
    private DatePicker datePicker;

    private final ServicesPerson servicesPerson = new ServicesPerson();

    private MainController msc;

    @Override
    public void setParent(MainController msc) {
        this.msc = msc;
    }


    public void showPersonInfo() {
        Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            textName.setText(selectedPerson.getName());
            textSurname.setText(selectedPerson.getSurname());
            textAge.setText(Integer.toString(selectedPerson.getAge()));
            datePicker.setValue(selectedPerson.getBirthDate());

            if (selectedPerson.isMale()) {
                toggleSexo.selectToggle(radioMale);
            } else {
                toggleSexo.selectToggle(radioFemale);
            }
        }
    }

    public void updatePerson() {
        Person selectedPerson = personListView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Person newPerson = new Person();
            try {
                newPerson.setName(textName.getText());
                newPerson.setSurname(textSurname.getText());
                newPerson.setAge(Integer.parseInt(textAge.getText()));
                newPerson.setMale(radioMale.isSelected());
                newPerson.setBirthDate(datePicker.getValue());
            } catch (NumberFormatException numEx) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(Strings.WARNING_CHECK_AGE);
                alert.showAndWait();
            }

            if (servicesPerson.updatePerson(selectedPerson, newPerson)) {
                personListView.getItems().add(newPerson);
                personListView.getItems().remove(selectedPerson);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(Strings.ERROR_UNEXPECTED);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(Strings.WARNING_SELECT_PERSON);
            alert.showAndWait();
        }
    }

    @Override
    public void showData() {
        personListView.getItems().clear();
        personListView.getItems().addAll(servicesPerson.getPersonList());
    }
}
