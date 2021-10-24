package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.Strings;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource(Strings.FXML_MAIN_PANE));
        BorderPane rootScreen = loaderMenu.load();
        Scene scene = new Scene(rootScreen);
        primaryStage.setTitle(Strings.WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
