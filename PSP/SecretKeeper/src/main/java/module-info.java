module SecretKeeper {
    requires javafx.fxml;
    requires javafx.controls;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires lombok;
    requires org.apache.logging.log4j;
    requires org.yaml.snakeyaml;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires com.google.common;
    requires io.vavr;
    requires spring.tx;
    requires org.bouncycastle.provider;


    exports org.quevedo.secretkeeper.gui.di;
    exports org.quevedo.secretkeeper.gui.controllers;
    exports org.quevedo.secretkeeper.dao.utils;
    exports org.quevedo.secretkeeper.dao.security;
    exports org.quevedo.secretkeeper.dao.security.di;
    exports org.quevedo.secretkeeper.services;
    exports org.quevedo.secretkeeper.dao.spring;
    exports org.quevedo.secretkeeper.model;

    opens org.quevedo.secretkeeper.gui.utils;
    opens org.quevedo.secretkeeper.gui.di;
    opens org.quevedo.secretkeeper.gui.controllers;
    opens org.quevedo.secretkeeper.config;
    opens org.quevedo.secretkeeper.dao.utils;
    opens org.quevedo.secretkeeper.dao.security;
}