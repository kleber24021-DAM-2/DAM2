package gui.controllers.purchases;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Item;
import services.CustomersServices;
import services.ItemsServices;
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

        if (purchaseToUpdate != null && itemToPurchase != null && localDate != null && customer != null) {
            Purchase updatedPurchase = new Purchase();
            updatedPurchase.setIdPurchase(purchaseToUpdate.getIdPurchase());
            updatedPurchase.setIdItem(itemToPurchase.getIdItem());
            updatedPurchase.setDate(localDate.toString());
//            updatedPurchase.setCustomersByIdCustomer(customer);
            purchasesServices.updatePurchase(updatedPurchase)
                    .peek(__ -> loadAllLists())
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, select all the fields to update the purchase");
            alert.showAndWait();
        }
    }

    public void loadAllLists() {
        PurchasesServices purchasesServices = new PurchasesServices();
        ItemsServices itemsServices = new ItemsServices();
        CustomersServices customersServices = new CustomersServices();

        purchasesServices.getAllPurchases()
                .peek(purchases -> purchaseList.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        itemsServices.getAllItems()
                .peek(purchases -> itemBox.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        customersServices.getAllCustomers()
                .peek(customers -> customerBox.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));


    }

    @FXML
    private void showPurchaseInfo(){
        Purchase purchase = purchaseList.getSelectionModel().getSelectedItem();
        if (purchase != null){
            itemBox.getSelectionModel().select(purchase.getIdItem());
            dateBox.setValue(LocalDate.parse(purchase.getDate()));
//            customerBox.getSelectionModel().select(purchase.getCustomersByIdCustomer());
        }
    }
}
