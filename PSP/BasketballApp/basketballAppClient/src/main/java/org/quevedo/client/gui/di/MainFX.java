package org.quevedo.client.gui.di;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.quevedo.client.gui.utils.FxmlPaths;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFX {
    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(FxmlPaths.MAIN_SCREEN));
            fxmlParent.getStylesheets().add(getClass().getResource(FxmlPaths.STYLE).toExternalForm());
            stage.setScene(new Scene(fxmlParent));
            stage.setResizable(false);
            stage.setTitle(FxmlPaths.SCREEN_NAME);
            stage.show();
        } catch (IOException io) {
            log.log(Level.ERROR, io);
        }
    }
}
