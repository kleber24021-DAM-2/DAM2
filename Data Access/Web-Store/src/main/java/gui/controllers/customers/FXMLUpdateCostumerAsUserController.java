package gui.controllers.customers;

import gui.controllers.FXMLPrincipalController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;

public class FXMLUpdateCostumerAsUserController {
    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField addressBox;

    private FXMLPrincipalController parent;

    @FXML
    private void updateInfo() {
        CustomersServices customersServices = new CustomersServices();

        String name = nameBox.getText();
        String phone = phoneBox.getText();
        String address = addressBox.getText();
        Customer customer = new Customer(parent.getLoggedUser().getUserId(), name, phone, address, parent.getLoggedUser());

        if (customersServices.updateCustomer(customer)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your information has been updated");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your information hasn't been updated. Please try again");
            alert.showAndWait();
        }
    }

    public void loadUserInfo(){
        CustomersServices customersServices = new CustomersServices();
        Customer loggedCustomer = customersServices.searchById(parent.getLoggedUser().getUserId());
        nameBox.setText(loggedCustomer.getName());
        phoneBox.setText(loggedCustomer.getTelephone());
        addressBox.setText(loggedCustomer.getAddress());
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
