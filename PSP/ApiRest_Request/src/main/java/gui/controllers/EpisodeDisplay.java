package gui.controllers;

import dao.models.characters.RickMortyCharacter;
import dao.models.episodes.Episode;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.ServicesCharacter;

public class EpisodeDisplay {
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNumber;
    @FXML
    private ListView<RickMortyCharacter> listViewCharacters;

    public void setInfo(Episode episode) {
        ServicesCharacter services = new ServicesCharacter();
        txtDate.setText(episode.getAirDate());
        txtName.setText(episode.getName());
        txtNumber.setText(episode.getEpisode());
        episode.getCharacters().forEach(s -> listViewCharacters.getItems().add(services.getCharacterByURL(s)));
    }


}
