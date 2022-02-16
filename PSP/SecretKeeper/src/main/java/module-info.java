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
    exports org.quevedo.secretkeeper.security.di;
    exports org.quevedo.secretkeeper.services;
    exports org.quevedo.secretkeeper.dao.api;
    exports org.quevedo.secretkeeper.model;
    exports org.quevedo.secretkeeper.config;
    exports org.quevedo.secretkeeper.dao.implementations;
    exports org.quevedo.secretkeeper.security.utils;

    opens org.quevedo.secretkeeper.gui.utils;
    opens org.quevedo.secretkeeper.gui.di;
    opens org.quevedo.secretkeeper.gui.controllers;
    opens org.quevedo.secretkeeper.config;
    opens org.quevedo.secretkeeper.dao.utils;
    exports org.quevedo.secretkeeper.security.simetrical;
    opens org.quevedo.secretkeeper.security.simetrical;
    opens org.quevedo.secretkeeper.security.utils;
    exports org.quevedo.secretkeeper.security.asimetrical;
    opens org.quevedo.secretkeeper.security.asimetrical;
}