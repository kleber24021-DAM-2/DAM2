package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.FxmlPaths;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoadScreen implements Initializable {

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(FxmlPaths.IMAGE_PATH))));
    }
}
