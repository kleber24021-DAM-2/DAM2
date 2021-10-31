package gui.controllers;

import dao.models.ownmodels.OwnCharacter;
import dao.models.ownmodels.OwnEpisode;
import dao.models.ownmodels.OwnLocation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import utils.FxmlPaths;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class MainController implements Initializable {

    private final FXMLLoader filterCharacterLoader;
    private final FXMLLoader characterLoader;
    private final FXMLLoader episodeLoader;
    private final FXMLLoader locationLoader;
    private final FXMLLoader loadScreenLoader;

    @FXML
    public BorderPane root;
    private AnchorPane filterCharacterPane;
    private AnchorPane characterPane;
    private CharacterDisplay characterController;
    private AnchorPane episodePane;
    private EpisodeDisplay episodeController;
    private AnchorPane locationPane;
    private LocationScreen locationController;
    private AnchorPane loadScreenPane;

    @Inject
    public MainController(FXMLLoader filterCharacterLoader, FXMLLoader characterLoader, FXMLLoader episodeLoader, FXMLLoader locationLoader, FXMLLoader loadScreenLoader) {
        this.filterCharacterLoader = filterCharacterLoader;
        this.characterLoader = characterLoader;
        this.episodeLoader = episodeLoader;
        this.locationLoader = locationLoader;
        this.loadScreenLoader = loadScreenLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadCharacter();
        preloadEpisode();
        preloadFilter();
        preloadLocation();
        preloadLoadScreen();

        showFilter();
    }

    private void preloadLoadScreen() {
        try {
            loadScreenPane = loadScreenLoader.load(getClass().getResourceAsStream(FxmlPaths.LOAD_SCREEN));
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }

    public void showLoadScreen() {
        root.setCenter(loadScreenPane);
    }


    private void preloadFilter() {
        try {
            filterCharacterPane = filterCharacterLoader.load(getClass().getResourceAsStream(FxmlPaths.FILTER_CHARACTER));
            FilterCharacter filterCharacterController = filterCharacterLoader.getController();
            filterCharacterController.setParent(this);
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }

    public void showFilter() {
        root.setCenter(filterCharacterPane);
    }

    private void preloadCharacter() {
        try {
            characterPane = characterLoader.load(getClass().getResourceAsStream(FxmlPaths.CHARACTER_DISPLAY));
            characterController = characterLoader.getController();
            characterController.setParent(this);
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }

    public void showCharacter(OwnCharacter selectedCharacter) {
        characterController.setInfo(selectedCharacter);
        root.setCenter(characterPane);
    }

    public void showCharacter() {
        root.setCenter(characterPane);
    }

    private void preloadEpisode() {
        try {
            episodePane = episodeLoader.load(getClass().getResourceAsStream(FxmlPaths.EPISODE_SCREEN));
            episodeController = episodeLoader.getController();
            episodeController.setParent(this);
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }

    public void showEpisode(OwnEpisode episode) {
        episodeController.setInfo(episode);
        root.setCenter(episodePane);
    }

    private void preloadLocation() {
        try {
            locationPane = locationLoader.load(getClass().getResourceAsStream(FxmlPaths.LOCATION_SCREEN));
            locationController = locationLoader.getController();
            locationController.setParent(this);
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }

    public void showLocation(OwnLocation location) {
        locationController.setInfo(location);
        root.setCenter(locationPane);
    }

    public BorderPane getRoot() {
        return root;
    }
}
