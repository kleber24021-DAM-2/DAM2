package gui.controllers;

import dao.models.ownmodels.OwnCharacter;
import dao.models.ownmodels.OwnEpisode;
import dao.utils.StringsUtils;
import gui.utils.UserMessages;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.ServicesCharacter;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EpisodeDisplay {
    ServicesCharacter services;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNumber;
    @FXML
    private ListView<OwnCharacter> listViewCharacters;
    private MainController parent;

    @Inject
    public EpisodeDisplay(ServicesCharacter services) {
        this.services = services;
    }

    public void setInfo(OwnEpisode episode) {
        DateTimeFormatter spanishFormat = DateTimeFormatter.ofPattern(StringsUtils.SPANISH_FORMATTER, Locale.forLanguageTag("es-ES"));
        txtDate.setText(episode.getAirDate().format(spanishFormat));
        txtName.setText(episode.getName());
        txtNumber.setText(episode.getEpisode());
        episode.getCharacters().forEach(s -> {
            services.getCharacterByURL(s).peek(p -> listViewCharacters.getItems().add(p))
                    .peekLeft(p -> new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_SERVER + "\n" + p).showAndWait());

        });
    }

    @FXML
    private void goBack() {
        parent.showCharacter();
    }

    public void setParent(MainController mainController) {
        parent = mainController;
    }
}
