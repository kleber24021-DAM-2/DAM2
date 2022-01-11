module basketballAppClient {
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
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;

    exports org.quevedo.client.gui.di;
    exports org.quevedo.client.dao.implementations;
    exports org.quevedo.client.services;
    exports org.quevedo.client.gui.controllers;
    exports org.quevedo.client.dao.retrofit;
    exports org.quevedo.client.dao.retrofit.interfaces;

    opens org.quevedo.client.gui.di;
    opens org.quevedo.client.dao.utils;
    opens org.quevedo.client.config;
    opens org.quevedo.client.dao.retrofit;
    opens org.quevedo.client.gui.utils;
    opens org.quevedo.client.services;
    opens org.quevedo.client.gui.controllers;
}