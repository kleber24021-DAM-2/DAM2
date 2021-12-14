package org.quevedo.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.quevedo.gui.utils.FxmlPaths;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class MainScreenController implements Initializable {

    @Inject
    public MainScreenController(FXMLLoader crudPersonasLoader,
                                FXMLLoader defuncionesLoader,
                                FXMLLoader filterPersonasLoader,
                                FXMLLoader matrimoniosLoader,
                                FXMLLoader nacimientosLoader){
        this.crudPersonasLoader = crudPersonasLoader;
        this.defuncionesLoader = defuncionesLoader;
        this.filterPersonasLoader = filterPersonasLoader;
        this.matrimoniosLoader = matrimoniosLoader;
        this.nacimientosLoader = nacimientosLoader;
    }


    AnchorPane crudPersonasPane;
    CrudPersonas crudPersonasController;
    FXMLLoader crudPersonasLoader;

    AnchorPane defuncionesPane;
    DefuncionesController defuncionesController;
    FXMLLoader defuncionesLoader;

    AnchorPane filterPersonasPane;
    FilterPersonasController filterPersonasController;
    FXMLLoader filterPersonasLoader;

    AnchorPane matrimoniosPane;
    MatrimoniosController matrimoniosController;
    FXMLLoader matrimoniosLoader;

    AnchorPane nacimientosPane;
    NacimientosController nacimientosController;
    FXMLLoader nacimientosLoader;

    @FXML
    private BorderPane root;

    private void preloadCrud(){
        try {
            crudPersonasPane = crudPersonasLoader.load(getClass().getResourceAsStream(FxmlPaths.CRUD_PERSONAS));
            crudPersonasController = crudPersonasLoader.getController();
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    private void preloadFilter(){
        try {
            filterPersonasPane = filterPersonasLoader.load(getClass().getResourceAsStream(FxmlPaths.FILTER));
            filterPersonasController = filterPersonasLoader.getController();
        }catch (IOException ioException){
            log.log(Level.ERROR, ioException);
        }
    }

    private void preloadDefunciones(){
        try {
            defuncionesPane = defuncionesLoader.load(getClass().getResourceAsStream(FxmlPaths.DEFUNCIONES));
            defuncionesController = defuncionesLoader.getController();
        }catch (IOException ioException){
            log.log(Level.ERROR, ioException);
        }
    }

    private void preloadMatrimonios(){
        try {
            matrimoniosPane = matrimoniosLoader.load(getClass().getResourceAsStream(FxmlPaths.MATRIMONIOS));
            matrimoniosController = matrimoniosLoader.getController();
        }catch (IOException ioException){
            log.log(Level.ERROR, ioException);
        }
    }

    private void preloadNacimientos(){
        try {
            nacimientosPane = nacimientosLoader.load(getClass().getResourceAsStream(FxmlPaths.NACIMIENTOS));
            nacimientosController = nacimientosLoader.getController();
        }catch (IOException ioException){
            log.log(Level.ERROR, ioException);
        }
    }

    @FXML
    private void showCrudPersonas() {
        crudPersonasController.setLista();
        root.setCenter(crudPersonasPane);
    }

    @FXML
    private void showFiltro() {
        root.setCenter(filterPersonasPane);
    }

    @FXML
    private void showDefunciones() {
        defuncionesController.setLista();
        root.setCenter(defuncionesPane);
    }

    @FXML
    private void showMatrimonios() {
        matrimoniosController.setLista();
        root.setCenter(matrimoniosPane);
    }

    @FXML
    private void showNacimientos() {
        nacimientosController.setLista();
        root.setCenter(nacimientosPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadCrud();
        preloadDefunciones();
        preloadFilter();
        preloadMatrimonios();
        preloadNacimientos();
    }
}