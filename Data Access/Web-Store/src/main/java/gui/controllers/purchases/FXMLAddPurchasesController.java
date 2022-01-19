/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.purchases;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Customer;
import model.Item;
import model.Purchase;

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

    
    public void load()
    {
//        loadCustomersList();
//        loadItemsList();
//        loadPurchasesList();
    }
    
    public void loadPurchasesList() {
//        PurchasesServices services = new PurchasesServices();
//        List<Purchase> purchasesList = services.getAllPurchases();
//        purchaseList.getItems().setAll(purchasesList);
    }

    public void loadItemsList() {
//        ItemsServices services = new ItemsServices();
//        List<Item> itemList = services.getAllItems();
//        itemBox.getItems().setAll(itemList);
    }

    public void loadCustomersList() {
//        CustomersServices service = new CustomersServices();
//        List<Customer> customerList = service.getAllCustomers();
//        customerBox.getItems().setAll(customerList);
    }

    public void addPurchase() {
//        PurchasesServices service = new PurchasesServices();
//        Purchase addedPurchase = service.addPurchase(customerBox.getSelectionModel().getSelectedItem().getIdCustomer(),
//                itemBox.getSelectionModel().getSelectedItem().getIdItem(),
//                dateBox.getValue());
//        purchaseList.getItems().add(addedPurchase);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
