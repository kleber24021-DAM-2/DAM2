/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.user.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLWelcomeController implements Initializable {

    @FXML
    private Label fxWelcomeTitle;
    
    @FXML
    private AnchorPane fxWelcomePane;

    // String con el nombre de usuario que le paso desde el login.
    private User login;
    
    public User getLogin() {
        return login;
    }
    
    //Acceso a la principal, y otras. (No necesario en menu, pero para botones?)
    
    private FXMLPrincipalController principal;
    private FXMLLoginController loginPane;
    
    

    public void setLogin(User login) {
        this.login = login;
        fxWelcomeTitle.setText("Welcome " + login.getUsername());
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fxWelcomeTitle.setText("Welcome back " + login);
    }    
    
}
