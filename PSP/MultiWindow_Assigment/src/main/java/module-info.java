module MultiWindow.Assigment {
    //needed for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires java.logging;
    requires MaterialFX;


    //requires javafx.swing;

    //needed for HTTP
    // requires unirest.java;

    //needed for JSON
//    requires gson;
//    requires java.sql;

    //needed for JavaFX
    opens controllers to javafx.fxml;

//    //needed for JSON
//    opens com.edencoding.models.openVision to gson;
//    opens com.edencoding.models.dogs to gson;

    exports main;
    exports models;
    exports services;
    exports utils;
}
