/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Customer;
import services.CustomersServices;
import services.PurchasesServices;

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
        customerBox.getItems().setAll(services.getAllCustomers());
    }
    
    public void deleteCustomer() {
        CustomersServices customersServices = new CustomersServices();
        PurchasesServices purchasesServices  = new PurchasesServices();
        Customer toDelete = customerBox.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.ERROR);
        if (toDelete == null){
            alert.setContentText("Please, select customer to delete");
            alert.showAndWait();
            return;
        }
        if (!customersServices.deleteCustomer(toDelete)){
            alert.setAlertType(AlertType.CONFIRMATION);
            alert.setContentText("There are purchases related to this customer. Would you like to delete them?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK){
                purchasesServices.deletePurchaseByCustomerId(toDelete.getIdCustomer());
                customersServices.deleteCustomer(toDelete);
            }
        }
        loadCustomersList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomersList();
    }

}
