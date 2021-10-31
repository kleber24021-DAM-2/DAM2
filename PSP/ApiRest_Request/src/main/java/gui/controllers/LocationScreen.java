package gui.controllers;

import dao.models.ownmodels.OwnCharacter;
import dao.models.ownmodels.OwnLocation;
import gui.utils.UserMessages;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.ServicesCharacter;

import javax.inject.Inject;

public class LocationScreen {

    @FXML
    private ListView<OwnCharacter> charactersListView;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtDimension;
    private final ServicesCharacter service;
    private MainController parent;
    @Inject
    public LocationScreen(ServicesCharacter service) {
        this.service = service;
    }

    public void setInfo(OwnLocation location) {
        txtName.setText(location.getName());
        txtType.setText(location.getType());
        txtDimension.setText(location.getDimension());
        location.getResidents().forEach(s -> service.getCharacterByURL(s)
                .peek(p -> charactersListView.getItems().add(p))
                .peekLeft(p -> new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_SERVER +"\n" + p).showAndWait()));

    }

    public void setParent(MainController mainController) {
        parent = mainController;
    }

    public void goBack() {
        parent.showCharacter();
    }
}
