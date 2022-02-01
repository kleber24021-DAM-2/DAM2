/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.customers;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.customer.Customer;
import services.CustomersServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLfindCustomerController implements Initializable {

    @FXML
    private TextField dniBox;
    @FXML
    private ListView<Customer> customerList;
    
     public void searchById() {
         CustomersServices service = new CustomersServices();
         String customerDni;
         try{
            customerDni = dniBox.getText();
            service.searchById(customerDni)
                    .peek(customer -> customerList.getItems().setAll(customer))
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
         }catch (NumberFormatException e){
             UiUtils.showAlert("Please, introduce a valid ID", Alert.AlertType.WARNING);
         }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize not needed in this screen
    }    
    
}
