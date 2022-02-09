package org.quevedo.secretkeeper.gui.di;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.gui.controllers.MainController;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFX {
    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(GuiConsts.MAIN_SCREEN));
            fxmlParent.getStylesheets().add(getClass().getResource(GuiConsts.STYLE).toExternalForm());
            stage.setScene(new Scene(fxmlParent));
            stage.setResizable(false);
            stage.setTitle(GuiConsts.SCREEN_NAME);
            stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> onClose(fxmlLoader.getController()));
            stage.show();
        } catch (IOException io) {
            log.error(io.getMessage(), io);
        }
    }

    private void onClose(MainController mainController) {
        mainController.onClose();
    }
}
