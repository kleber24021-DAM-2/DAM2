/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.customers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;

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
         Alert alert = new Alert(AlertType.WARNING);
        int customerId;
         try{
            customerId = Integer.parseInt(dniBox.getText());
         }catch (NumberFormatException e){
             alert.setContentText("Please, introduce a valid ID");
             alert.showAndWait();
             return;
         }
         Customer returnedCustomer = service.searchById(customerId);
         if (returnedCustomer == null){
             alert.setContentText("There are no customers with that ID");
             alert.showAndWait();
             return;
         }
         customerList.getItems().setAll(returnedCustomer);
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize not needed in this screen
    }    
    
}
