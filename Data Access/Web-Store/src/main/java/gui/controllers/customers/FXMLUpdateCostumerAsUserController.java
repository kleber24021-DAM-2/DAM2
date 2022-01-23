package gui.controllers.customers;

import gui.controllers.FXMLPrincipalController;
import gui.controllers.UiUtils;
import io.vavr.control.Either;
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

        Either<String, Customer> checkResult = customersServices.searchById(parent.getLoggedUser().getUserId());
        if (checkResult.isRight()) {
            Customer toUpdate = checkResult.get();
            toUpdate.setName(name);
            toUpdate.setTelephone(phone);
            toUpdate.setAddress(address);
            customersServices.updateCustomer(toUpdate)
                    .peek(__ -> UiUtils.showAlert("Your information has been updated", Alert.AlertType.INFORMATION))
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        } else {
            UiUtils.showAlert(checkResult.getLeft(), Alert.AlertType.ERROR);
        }
    }

    public void loadUserInfo() {
        CustomersServices customersServices = new CustomersServices();
        customersServices.searchById(parent.getLoggedUser().getUserId())
                .peek(loggedCustomer -> {
                    nameBox.setText(loggedCustomer.getName());
                    phoneBox.setText(loggedCustomer.getTelephone());
                    addressBox.setText(loggedCustomer.getAddress());
                })
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
