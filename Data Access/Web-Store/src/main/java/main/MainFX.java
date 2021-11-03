/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.controllers.FXMLPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Laura
 */
public class MainFX extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/FXMLPrincipal.fxml"));
        BorderPane root = loaderMenu.load();


        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/style/dark-theme.css")).toExternalForm()));
        primaryStage.setTitle("Web store");
        primaryStage.setScene(scene);
        FXMLPrincipalController controller = loaderMenu.getController();
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::closeApplication);
        primaryStage.show();
        //para no poder maximizar pantalla y
        primaryStage.setResizable(false);
    }

}
