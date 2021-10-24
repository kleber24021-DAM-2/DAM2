package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/PrimeraInterfaz.fxml"));
        AnchorPane root = loaderMenu.load();
        Scene scene = new Scene(root);
        stage.setTitle("Mi primera ventana de prueba");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
