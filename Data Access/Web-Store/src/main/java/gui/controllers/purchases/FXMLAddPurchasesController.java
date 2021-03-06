/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.purchases;


import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLAddPurchasesController implements Initializable {


    @FXML
    private ComboBox<Customer> customerBox;
    @FXML
    private ComboBox<Item> itemBox;
    @FXML
    private ListView<Purchase> purchaseList;
    @FXML
    private DatePicker dateBox;


    public void load() {
        loadCustomersList();
        loadItemsList();
        loadPurchasesList();
    }

    public void loadPurchasesList() {
        PurchasesServices services = new PurchasesServices();
        services.getAllPurchases()
                .peek(purchases -> purchaseList.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

    }

    public void loadItemsList() {
        ItemsServices services = new ItemsServices();
        services.getAllItems()
                .peek(items -> itemBox.getItems().setAll(items))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    public void loadCustomersList() {
        CustomersServices service = new CustomersServices();
        service.getAllCustomers()
                .peek(customers -> customerBox.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    public void addPurchase() {
        PurchasesServices service = new PurchasesServices();
        Purchase toAddPurchase = Purchase.builder().build();
        if (!customerBox.getSelectionModel().isEmpty() && dateBox.getValue() != null && !itemBox.getSelectionModel().isEmpty()) {
            toAddPurchase.setDate(dateBox.getValue().toString());
            toAddPurchase.setIdItem(itemBox.getSelectionModel().getSelectedItem().getId().toString());
            service.addPurchase(toAddPurchase, customerBox.getSelectionModel().getSelectedItem())
                    .peek(purchase -> purchaseList.getItems().add(purchase))
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        }else {
            UiUtils.showAlert("Please, check all fields are complete", Alert.AlertType.WARNING);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
