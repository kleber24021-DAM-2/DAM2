package gui.controllers.customers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;

public class FXMLUpdateCostumerController {
    @FXML
    private ListView<Customer> customerListView;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField addressBox;

    @FXML
    private void updateCustomer() {
        CustomersServices customersServices = new CustomersServices();
        String name = nameBox.getText();
        String phone = phoneBox.getText();
        String address = addressBox.getText();
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();

        if (!(name.isEmpty() || name.isBlank() || phone.isEmpty() || phone.isBlank() || address.isEmpty() || address.isBlank())) {
            try {
                Customer updatedCustomer = new Customer(selectedCustomer.getIdCustomer(), name, phone, address);
                customersServices.updateCustomer(updatedCustomer);
                loadAllLists();
                clearTextFields();
            } catch (NullPointerException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please, select a customer to update it");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, fill all the fields to update");
            alert.showAndWait();
        }
    }

    public void loadAllLists() {
        CustomersServices customersServices = new CustomersServices();
        customerListView.getItems().setAll(customersServices.getAllCustomers());
    }

    @FXML
    public void showCustomerInfo() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            nameBox.setText(selectedCustomer.getName());
            phoneBox.setText(selectedCustomer.getPhone());
            addressBox.setText(selectedCustomer.getAddress());
        }
    }

    private void clearTextFields(){
        nameBox.clear();
        phoneBox.clear();
        addressBox.clear();
    }
}
