/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Purchase;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLDeleteController implements Initializable {

    @FXML
    private ListView<Purchase> purchaseBox;


    public void loadPurchases() {
//        PurchasesServices services = new PurchasesServices();
//        purchaseBox.getItems().setAll(services.getAllPurchases());
    }
    
    public void deletePurchase(){
//        PurchasesServices services = new PurchasesServices();
//        Purchase selectedPurchase;
//        selectedPurchase = purchaseBox.getSelectionModel().getSelectedItem();
//        if (selectedPurchase == null){
//            Alert alert = new Alert(AlertType.WARNING, "Please select a purchase to delete");
//            alert.showAndWait();
//            return;
//        }
//        if (!services.deletePurchase(selectedPurchase)){
//            Alert alert = new Alert(AlertType.WARNING, "This purchase has an associated review. It can't be deleted");
//            alert.showAndWait();
//            return;
//        }
//        loadPurchases();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
