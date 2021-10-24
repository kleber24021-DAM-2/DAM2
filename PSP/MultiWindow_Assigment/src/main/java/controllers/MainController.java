package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import utils.Strings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    UpdatePersonController upc = new UpdatePersonController();
    private FXMLLoader createPersonLoader;
    private AnchorPane createPersonPane;
    private FXMLLoader deletePersonLoader;
    private AnchorPane deletePersonPane;
    private DeletePersonController dpc = new DeletePersonController();
    private FXMLLoader readPersonLoader;
    private AnchorPane readPersonPane;
    private ReadPersonController rpc = new ReadPersonController();
    private FXMLLoader updatePersonLoader;
    private AnchorPane updatePersonPane;
    private FXMLLoader welcomeLoader;
    private AnchorPane welcomePane;


    @FXML
    private BorderPane rootScreen;


    private void loadWelcomePane() {
        welcomeLoader = new FXMLLoader();
        try {
            if (welcomePane == null) {
                welcomePane = welcomeLoader.load(getClass().getResourceAsStream(Strings.FXML_WELCOME_PANE));
            }
        } catch (IOException io) {
            Logger.getLogger("ERROR: ").log(Level.SEVERE, "IO ERROR: " + io);
        }
    }

    @FXML
    private void showWelcomeScreen() {
        rootScreen.setCenter(welcomePane);
    }

    private void loadCreatePane() {
        createPersonLoader = new FXMLLoader();
        try {
            if (createPersonPane == null) {
                createPersonPane = createPersonLoader.load(getClass().getResourceAsStream(Strings.FXML_CREATE_PANE));
            }
        } catch (IOException io) {
            Logger.getLogger("ERROR: ").log(Level.SEVERE, "IO ERROR: " + io);
        }

    }


    @FXML
    private void showCreateScreen() {
        rootScreen.setCenter(createPersonPane);
    }


    private void loadReadPane() {
        readPersonLoader = new FXMLLoader();
        try {
            if (readPersonPane == null) {
                readPersonPane = readPersonLoader.load(getClass().getResourceAsStream(Strings.FXML_READ_PANE));
                rpc = readPersonLoader.getController();
                rpc.setParent(this);
            }
        } catch (IOException io) {
            Logger.getLogger("ERROR: ").log(Level.SEVERE, "IO ERROR: " + io);
        }
    }

    @FXML
    private void showReadScreen() {
        rpc.showData();
        rootScreen.setCenter(readPersonPane);
    }


    private void loadUpdatePane() {
        updatePersonLoader = new FXMLLoader();
        try {
            if (updatePersonPane == null) {
                updatePersonPane = updatePersonLoader.load(getClass().getResourceAsStream(Strings.FXML_UPDATE_PANE));
                upc = updatePersonLoader.getController();
                upc.setParent(this);
            }
        } catch (IOException io) {
            Logger.getLogger("ERROR: ").log(Level.SEVERE, "IO ERROR: " + io);
        }
    }

    @FXML
    private void showUpdateScreen() {
        upc.showData();
        rootScreen.setCenter(updatePersonPane);
    }


    private void loadDeletePane() {
        deletePersonLoader = new FXMLLoader();
        try {
            if (deletePersonPane == null) {
                deletePersonPane = deletePersonLoader.load(getClass().getResourceAsStream(Strings.FXML_DELETE_PANE));
                dpc = deletePersonLoader.getController();
                dpc.setParent(this);
            }
        } catch (IOException io) {
            Logger.getLogger("ERROR: ").log(Level.SEVERE, "IO ERROR: " + io);
        }
    }

    @FXML
    private void showDeleteScreen() {
        rootScreen.setCenter(deletePersonPane);
        dpc.showData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadReadPane();
        loadUpdatePane();
        loadDeletePane();
        loadWelcomePane();
        loadCreatePane();
        showWelcomeScreen();
    }
}
