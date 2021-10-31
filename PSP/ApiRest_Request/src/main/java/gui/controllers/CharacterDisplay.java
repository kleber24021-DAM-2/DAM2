package gui.controllers;

import dao.models.ownmodels.OwnCharacter;
import dao.models.ownmodels.OwnEpisode;
import dao.models.ownmodels.OwnLocation;
import gui.utils.UserMessages;
import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ServicesEpisodes;
import services.ServicesLocation;

import javax.inject.Inject;

public class CharacterDisplay {
    private final ServicesEpisodes servicesEpisodes;
    private final ServicesLocation servicesLocation;
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
    private OwnCharacter thisCharacter;
    private MainController parent;

    @Inject
    public CharacterDisplay(ServicesEpisodes servicesEpisodes, ServicesLocation servicesLocation) {
        this.servicesEpisodes = servicesEpisodes;
        this.servicesLocation = servicesLocation;
    }

    public void setInfo(OwnCharacter character) {
        thisCharacter = character;
        Image image = new Image(character.getImageUrl());
        imageView.setImage(image);
        txtName.setText(character.getName());
        txtStatus.setText(character.getStatus());
        txtSpecie.setText(character.getSpecies());
        txtGender.setText(character.getGender());
        if (character.getOriginName() != null) {
            txtOrigin.setText(character.getOriginName());
        } else {
            txtOrigin.setText(UserMessages.NOT_AVAILABLE);
        }
        if (character.getLocationName() != null) {
            txtLocation.setText(character.getLocationName());
        } else {
            txtLocation.setText(UserMessages.NOT_AVAILABLE);
        }
        if (character.getType() != null) {
            txtType.setText(character.getType());
        } else {
            txtType.setText(UserMessages.NOT_AVAILABLE);
        }
        episodeList.getItems().setAll(character.getEpisodes());
    }

    @FXML
    private void showEpisode() {
        String selectedString = episodeList.getSelectionModel().getSelectedItem();
        if (selectedString != null) {
            var task = new Task<Either<String, OwnEpisode>>() {
                @Override
                protected Either<String, OwnEpisode> call() {
                    return servicesEpisodes.getEpisode(selectedString);
                }
            };
            task.setOnSucceeded(event -> {
                task.getValue()
                        .peek(p -> parent.showEpisode(p))
                        .peekLeft(p -> new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_SERVER + "\n" + p).showAndWait());
                this.parent.getRoot().setCursor(Cursor.DEFAULT);
            });
            task.setOnFailed(event -> {
                backToNormal();
                new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_APP).showAndWait();
            });


            goToLoadScreen();
            new Thread(task).start();
        }
    }

    @FXML
    private void showLocation() {
        if (thisCharacter.getLocationName() != null) {
            var task = new Task<Either<String, OwnLocation>>() {
                @Override
                protected Either<String, OwnLocation> call() {
                    return servicesLocation.getLocation(thisCharacter.getLocationUrl());
                }
            };
            task.setOnSucceeded(event -> {
                task.getValue()
                        .peek(p -> parent.showLocation(p))
                        .peekLeft(p -> new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_SERVER + "\n" + p));
                this.parent.getRoot().setCursor(Cursor.DEFAULT);
            });
            task.setOnFailed(event -> {
                backToNormal();
                new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_APP).showAndWait();
            });
            goToLoadScreen();
            new Thread(task).start();
        }
    }

    private void backToNormal() {
        this.parent.getRoot().setCursor(Cursor.DEFAULT);
        this.parent.showCharacter();
    }

    private void goToLoadScreen() {
        this.parent.showLoadScreen();
        this.parent.getRoot().setCursor(Cursor.WAIT);
    }

    @FXML
    private void goBack() {
        parent.showFilter();
    }

    public void setParent(MainController parent) {
        this.parent = parent;
    }
}
