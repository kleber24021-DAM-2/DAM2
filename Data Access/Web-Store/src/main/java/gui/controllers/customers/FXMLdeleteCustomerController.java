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
import model.Customer;
import services.CustomersServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLdeleteCustomerController implements Initializable {

    @FXML
    private ListView<Customer> customerBox;

    public void loadCustomersList() {
        CustomersServices services = new CustomersServices();
        services.getAllCustomers()
                .peek(customers -> customerBox.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }
    
    public void deleteCustomer() {
        CustomersServices customersServices = new CustomersServices();
        Customer toDelete = customerBox.getSelectionModel().getSelectedItem();

        customersServices.deleteCustomer(toDelete)
                .peek(__ -> customerBox.getItems().remove(toDelete))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
