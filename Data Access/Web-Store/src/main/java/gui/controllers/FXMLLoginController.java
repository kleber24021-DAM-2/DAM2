/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLLoginController implements Initializable {

    
    // Esto es para poder coger lo que pone en ese campo y meterlo en este caso en el atributo usuario
    // del controlador principal.
    @FXML
    private TextField fxUser;
    @FXML
    private TextField passBox;
    @FXML
    private Label errorBox;

    private UserService userService = new UserService();
    
    //Referencia al controlador principal para poder acceder a él, junto con su set para poder cambiarlo.
    private FXMLPrincipalController principal;

    public void setPrincipal(FXMLPrincipalController principal) {
        this.principal = principal;
    }
    
    
    public void clickLogin(){
        User inputUser = new User();
        inputUser.setUsername(fxUser.getText());
        inputUser.setPassword(passBox.getText());
        if (userService.checkUserPassword(inputUser) != null){
            principal.setLoggedUser(inputUser);
            if (inputUser.getUserId() == 0){
                principal.setForAdmin(true);
                principal.setForCustomer(false);
            }else{
                principal.setForAdmin(false);
                principal.setForCustomer(true);
            }
            principal.chargeWelcome();
        }else {
            errorBox.setText("El usuario o contraseña introducidos son incorrectos");
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Not needed.
    }    
    
}
