module nashvilleCensusClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires io.vavr;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires common;

    exports org.quevedo.gui.di;
    exports org.quevedo.gui.controllers;
    exports org.quevedo.dao.implementaciones;
    exports org.quevedo.dao.retrofit;
    exports org.quevedo.services;
    exports org.quevedo.dao.utils;
    exports org.quevedo.config;

    opens org.quevedo.config;
    opens org.quevedo.gui.di;
    opens org.quevedo.gui.utils;
    opens org.quevedo.dao.utils;
    opens org.quevedo.dao.retrofit;
    opens org.quevedo.dao.implementaciones;
    opens org.quevedo.gui.controllers;
    opens org.quevedo.services;
}