package gui.controllers;

import dao.models.characters.RickMortyCharacter;
import dao.models.locations.Location;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.ServicesCharacter;

public class LocationScreen {
    @FXML
    private ListView<RickMortyCharacter> charactersListView;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtDimension;

    public void setInfo(Location location) {
        txtName.setText(location.getName());
        txtType.setText(location.getType());
        txtDimension.setText(location.getDimension());

        ServicesCharacter service = new ServicesCharacter();

        location.getResidents().forEach(s -> charactersListView.getItems().add(service.getCharacterByURL(s)));

    }
}
