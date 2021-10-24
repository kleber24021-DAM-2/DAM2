package gui.controllers;

import dao.models.characters.CharacterResponse;
import dao.models.characters.RickMortyCharacter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServicesCharacter;
import utils.FxmlPaths;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilterCharacter {
    @FXML
    private TextField inputId;
    @FXML
    private Label pageLabel;

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private ComboBox<String> cbSpecies;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private ListView<RickMortyCharacter> listViewResults;

    private CharacterResponse actualResponse;
    private int actualPage;

    @FXML
    private void filterCharacters() {
        ServicesCharacter service = new ServicesCharacter();
        actualPage = 1;
        doQuery(
                txtName.getText(),
                cbStatus.getSelectionModel().getSelectedItem(),
                cbSpecies.getSelectionModel().getSelectedItem(),
                cbGender.getSelectionModel().getSelectedItem());
        refreshResultView();
    }

    @FXML
    private void goRight() {
        if (actualPage != actualResponse.getInfo().getPages()) {
            actualPage++;
            refreshResultView();
        }
    }

    @FXML
    private void goLeft() {
        if (actualPage != 1) {
            actualPage--;
            refreshResultView();
        }
    }

    private void refreshResultView() {
        if (doQuery(txtName.getText(),
                cbStatus.getSelectionModel().getSelectedItem(),
                cbSpecies.getSelectionModel().getSelectedItem(),
                cbGender.getSelectionModel().getSelectedItem())) {
            listViewResults.getItems().setAll(actualResponse.getResults());
            pageLabel.setText(actualPage + " / " + actualResponse.getInfo().getPages());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se han encontrado resultados");
            alert.showAndWait();
        }
    }

    private boolean doQuery(String name, String status, String species, String gender) {
        ServicesCharacter service = new ServicesCharacter();
        actualResponse = service.getFilteredCharacters(name, status, species, gender, actualPage);
        return actualResponse != null && !actualResponse.getResults().isEmpty();
    }

    @FXML
    private void clearAll() {
        txtName.clear();
        cbStatus.getSelectionModel().clearSelection();
        cbGender.getSelectionModel().clearSelection();
        cbSpecies.getSelectionModel().clearSelection();
    }

    @FXML
    private void getSelectedCharacter() {
        RickMortyCharacter selectedCharacter = listViewResults.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            openCharacterNewWindow(selectedCharacter);
        }
    }

    private void openCharacterNewWindow(RickMortyCharacter selectedCharacter) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(FxmlPaths.CHARACTER_DISPLAY));
            Stage stage = new Stage();
            stage.setTitle(selectedCharacter.getName());
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(txtName.getScene().getWindow());
            CharacterDisplay characterDisplay = fxmlLoader.getController();
            characterDisplay.setInfo(selectedCharacter);
            stage.showAndWait();
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
    }

    @FXML
    private void setCharacterByID() {
        ServicesCharacter service = new ServicesCharacter();
        try {
            RickMortyCharacter character = service.getCharacterByID(Integer.parseInt(inputId.getText()));
            openCharacterNewWindow(character);
        } catch (NumberFormatException wrongNum) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Introduzca un número en el campo de ID");
            alert.showAndWait();
        }

    }
}
