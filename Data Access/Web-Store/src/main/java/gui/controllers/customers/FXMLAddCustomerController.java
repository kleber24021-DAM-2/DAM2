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
import model.Customer;
import services.CustomersServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 */
public class FXMLAddCustomerController implements Initializable {

    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField addressBox;
    @FXML
    private ListView<Customer> customerListView;

    public void loadCustomersList() {
        CustomersServices services = new CustomersServices();
        services.getAllCustomers()
                        .peek(customers -> customerListView.getItems().setAll(customers))
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

     }

    public void addCustomer() {
        CustomersServices services = new CustomersServices();
        String name = nameBox.getText();
        String phone = phoneBox.getText();
        String address = addressBox.getText();
        Customer toSaveCustomer = new Customer();
        toSaveCustomer.setName(name);
        toSaveCustomer.setTelephone(phone);
        toSaveCustomer.setAddress(address);

        services.addCustomer(toSaveCustomer)
                .peek(customer -> {
                    customerListView.getItems().add(customer);
                    clearAll();
                })
                .peekLeft(error -> new Alert(Alert.AlertType.ERROR, error).showAndWait());
    }
    private void clearAll(){
        nameBox.clear();
        phoneBox.clear();
        addressBox.clear();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
