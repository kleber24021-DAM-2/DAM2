module ApiRest.Request {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires io.vavr;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    //needed for JavaFX
    opens gui;
    opens gui.controllers;
    opens config;
    opens dao.utils;
    opens utils;
    opens dao;
    // yaml
    //opens config to org.yaml.snakeyaml.Yaml;¡

    exports gui;
    exports dao.models.characters;
    exports dao;
    exports dao.models.episodes;
    exports dao.models;
    exports services;
    exports dao.models.locations;
    exports dao.models.ownmodels;
    exports gui.utils;
    opens gui.utils;
}