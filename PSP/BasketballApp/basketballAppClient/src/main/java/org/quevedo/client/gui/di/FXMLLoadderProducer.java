package org.quevedo.client.gui.di;

import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class FXMLLoadderProducer {
    @Inject
    Instance<Object> instance;

    @Produces
    public FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(param -> instance.select(param).get());
        return loader;
    }
}