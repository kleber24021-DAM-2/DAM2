package gui.controllers.purchases;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Customer;
import model.Item;
import model.Purchase;
import services.PurchasesServices;

import java.time.LocalDate;

public class FXMLUpdatePurchasesController {

    @FXML
    private ListView<Purchase> purchaseList;
    @FXML
    private ComboBox<Item> itemBox;
    @FXML
    private DatePicker dateBox;
    @FXML
    private ComboBox<Customer> customerBox;

    @FXML
    private void updatePurchase() {
        PurchasesServices purchasesServices = new PurchasesServices();
        Purchase purchaseToUpdate = purchaseList.getSelectionModel().getSelectedItem();
        Item itemToPurchase = itemBox.getSelectionModel().getSelectedItem();
        LocalDate localDate = dateBox.getValue();
        Customer customer = customerBox.getSelectionModel().getSelectedItem();

        if (purchaseToUpdate != null && itemToPurchase != null && localDate != null && customer != null){

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, select all the fields to update the purchase");
            alert.showAndWait();
        }
    }
}
