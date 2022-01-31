package gui.controllers.customers;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.customer.Address;
import model.customer.Customer;
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
        Address addressObject = new Address();
        addressObject.setCalle(address);
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();

        if (!(name.isEmpty() || phone.isEmpty() || address.isEmpty())) {
            try {
                selectedCustomer.setName(name);
                selectedCustomer.setTelephone(phone);
                selectedCustomer.setAddress(addressObject);
                customersServices.updateCustomer(selectedCustomer)
                        .peek(__ -> {
                            loadAllLists();
                            clearTextFields();
                        })
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

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
        customersServices.getAllCustomers()
                .peek(customers -> customerListView.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }


    @FXML
    public void showCustomerInfo() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            nameBox.setText(selectedCustomer.getName());
            phoneBox.setText(selectedCustomer.getTelephone());
            addressBox.setText(selectedCustomer.getAddress().getCalle());
        }
    }

    private void clearTextFields(){
        nameBox.clear();
        phoneBox.clear();
        addressBox.clear();
    }
}
