package controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.Person;
import services.ServicesPerson;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReadPersonController implements ChildController, Initializable {

    private final ServicesPerson service = new ServicesPerson();

    @FXML
    private MFXListView<Person> readListView;
    @FXML
    private MFXComboBox<String> filterComboBox;

    private MainController msc;

    @Override
    public void setParent(MainController msc) {
        this.msc = msc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterComboBox.getItems().addAll("Todos", "Hombre", "Mujer");
        filterComboBox.getSelectionModel().selectFirst();
    }

    @Override
    public void showData() {
        readListView.getItems().clear();
        readListView.getItems().addAll(service.getPersonList());
    }

    @FXML
    private void filterData() {
        String selectedOption = filterComboBox.getSelectedValue();
        List<Person> data = service.getFilteredData(selectedOption);
        readListView.getItems().clear();
        readListView.getItems().addAll(data);
    }
}
