package gui.controllers.customers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Customer;

public class FXMLListCustomerController {

    @FXML
    private ListView<Customer> customersList;

    public void loadList(){
//        CustomersServices customersServices = new CustomersServices();
//        customersList.getItems().setAll(customersServices.getAllCustomers());
    }
}
