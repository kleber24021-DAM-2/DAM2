module SecretKeeperClient {
    requires common;
    requires com.google.gson;
    requires io.vavr;
    requires lombok;
    requires retrofit2;
    requires okhttp3;
    requires jakarta.inject.api;
    requires javafx.graphics;
    requires jakarta.enterprise.cdi.api;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires org.yaml.snakeyaml;
    requires org.apache.logging.log4j;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.bouncycastle.provider;
    requires com.nimbusds.jose.jwt;
    requires com.google.common;

    exports org.quevedo.client.gui.di;
    exports org.quevedo.client.services;
    exports org.quevedo.client.security.utils;
    exports org.quevedo.client.dao.di.retrofit;
    exports org.quevedo.client.config;
    exports org.quevedo.client.gui.controllers;
    exports org.quevedo.client.security.di;
    exports org.quevedo.client.dao.implementations;
    exports org.quevedo.client.security.asimetrical;
    exports org.quevedo.client.dao.di.retrofit.interfaces;
    exports org.quevedo.client.dao.utils;
    exports org.quevedo.client.dao.interfaces;

    opens org.quevedo.client.dao.utils;
    opens org.quevedo.client.dao.di.retrofit;
    opens org.quevedo.client.gui.di;
    opens org.quevedo.client.gui.utils;
    opens org.quevedo.client.gui.controllers;
    exports org.quevedo.client.security.simetrical;
}