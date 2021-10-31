package gui.controllers;

import dao.models.ownmodels.OwnCharacter;
import gui.utils.UserMessages;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import services.ServicesCharacter;

import javax.inject.Inject;
import java.util.List;

@Log4j2
public class FilterCharacter {
    private final ServicesCharacter service;
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
    private ListView<OwnCharacter> listViewResults;
    private List<OwnCharacter> actualResponse;
    private int actualMaxPage;
    private int actualPage;
    private MainController parent;


    @Inject
    public FilterCharacter(ServicesCharacter service) {
        this.service = service;
    }

    @FXML
    private void filterCharacters() {
        actualPage = 1;
        doQuery(
                txtName.getText(),
                cbStatus.getSelectionModel().getSelectedItem(),
                cbSpecies.getSelectionModel().getSelectedItem(),
                cbGender.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void goRight() {
        if (actualPage != actualMaxPage) {
            actualPage++;
            doQuery(txtName.getText(),
                    cbStatus.getSelectionModel().getSelectedItem(),
                    cbSpecies.getSelectionModel().getSelectedItem(),
                    cbGender.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void goLeft() {
        if (actualPage != 1) {
            actualPage--;
            doQuery(txtName.getText(),
                    cbStatus.getSelectionModel().getSelectedItem(),
                    cbSpecies.getSelectionModel().getSelectedItem(),
                    cbGender.getSelectionModel().getSelectedItem());
        }
    }

    private void refreshResultView(boolean queryAnswer) {
        if (queryAnswer) {
            listViewResults.getItems().setAll(actualResponse);
            pageLabel.setText(actualPage + " / " + actualMaxPage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(UserMessages.EMPTY_QUERY);
            alert.showAndWait();
        }
    }

    private void doQuery(String name, String status, String species, String gender) {
        var task = new Task<Either<String, Tuple2<Integer, List<OwnCharacter>>>>() {
            @Override
            protected Either<String, Tuple2<Integer, List<OwnCharacter>>> call() {
                return service.getFilteredCharacters(name, status, species, gender, actualPage);
            }
        };
        task.setOnSucceeded(event -> {
            task.getValue()
                    .peek(p -> {
                        actualMaxPage = p._1;
                        actualResponse = p._2;
                        refreshResultView(actualResponse != null && !actualResponse.isEmpty());
                    })
                    .peekLeft(p -> new Alert(Alert.AlertType.ERROR, p).showAndWait());
            this.parent.getRoot().setCursor(Cursor.DEFAULT);
        });
        task.setOnFailed(event -> {
            this.parent.getRoot().setCursor(Cursor.DEFAULT);
            new Alert(Alert.AlertType.ERROR, UserMessages.ERROR_APP).showAndWait();
        });
        this.parent.getRoot().setCursor(Cursor.WAIT);
        new Thread(task).start();
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
        OwnCharacter selectedCharacter = listViewResults.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            showCharacterDisplay(selectedCharacter);
        }
    }

    private void showCharacterDisplay(OwnCharacter selectedCharacter) {
        parent.showCharacter(selectedCharacter);
    }

    @FXML
    private void setCharacterByID() {
        Either<String, OwnCharacter> serviceResult = null;
        try {
            serviceResult = service.getCharacterByID(Integer.parseInt(inputId.getText()));
        } catch (NumberFormatException wrongNum) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(UserMessages.ERROR_NUMBER_INVALID);
            alert.showAndWait();
            return;
        }
        serviceResult.peek(this::showCharacterDisplay)
                .peekLeft(p -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, p);
                    alert.showAndWait();
                });
    }

    public void setParent(MainController mainController) {
        parent = mainController;
    }
}
