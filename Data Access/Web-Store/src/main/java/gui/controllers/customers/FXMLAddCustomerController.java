/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.customers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;

import java.net.URL;
import java.util.List;
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
        List<Customer> customersList = services.getAllCustomers();
        customerListView.getItems().setAll(customersList);
     }

    public void addCustomer() {
        CustomersServices services = new CustomersServices();
        String name = nameBox.getText();
        String phone = phoneBox.getText();
        String address = addressBox.getText();

        if (services.addCustomer(-1, name, phone, address)){
            loadCustomersList();
            clearAll();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Customer not added to the database. Please check the data introduced and try again. (Possible repeated ID)");
            alert.showAndWait();
        }
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
        loadCustomersList();
    }

}
