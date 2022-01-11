package org.quevedo.client.gui.di;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;

public class StartProgram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }
}
