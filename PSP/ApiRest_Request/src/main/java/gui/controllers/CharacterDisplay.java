package gui.controllers;

import dao.models.characters.RickMortyCharacter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServicesEpisodes;
import services.ServicesLocation;
import utils.FxmlPaths;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CharacterDisplay {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtStatus;
    @FXML
    private TextField txtSpecie;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtOrigin;
    @FXML
    private ListView<String> episodeList;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtType;

    private RickMortyCharacter thisCharacter;

    public void setInfo(RickMortyCharacter character) {
        thisCharacter = character;
        Image image = new Image(character.getImage());
        imageView.setImage(image);
        txtName.setText(character.getName());
        txtStatus.setText(character.getStatus());
        txtSpecie.setText(character.getSpecies());
        txtGender.setText(character.getGender());
        if (character.getOrigin() != null) {
            txtOrigin.setText(character.getOrigin().getName());
        } else {
            txtOrigin.setText("N/A");
        }
        if (character.getLocation() != null) {
            txtLocation.setText(character.getLocation().getName());
        } else {
            txtLocation.setText("N/A");
        }
        if (character.getType() != null) {
            txtType.setText(character.getType());
        } else {
            txtType.setText("N/A");
        }
        episodeList.getItems().setAll(character.getEpisode());
    }

    @FXML
    private void showEpisode() {
        ServicesEpisodes servicesEpisodes = new ServicesEpisodes();
        String selectedString = episodeList.getSelectionModel().getSelectedItem();
        if (selectedString != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(FxmlPaths.EPISODE_SCREEN));
                Stage stage = new Stage();
                stage.setTitle(selectedString);
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(txtName.getScene().getWindow());
                EpisodeDisplay episodeDisplay = fxmlLoader.getController();
                episodeDisplay.setInfo(servicesEpisodes.getEpisode(selectedString));
                stage.showAndWait();
            } catch (IOException io) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
            }
        }
    }

    @FXML
    private void showLocation() {
        ServicesLocation services = new ServicesLocation();
        if (thisCharacter.getLocation() != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource(FxmlPaths.LOCATION_SCREEN));
                Stage stage = new Stage();
                stage.setTitle(thisCharacter.getLocation().getName());
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(txtName.getScene().getWindow());
                LocationScreen locationScreen = fxmlLoader.getController();
                locationScreen.setInfo(services.getLocation(thisCharacter.getLocation().getUrl()));
                stage.showAndWait();
            } catch (IOException io) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
            }
        }
    }
}
