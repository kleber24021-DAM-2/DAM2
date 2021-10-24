/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import services.PurchasesServices;
import model.Purchase;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLDeleteController implements Initializable {

    @FXML
    private ListView<Purchase> purchaseBox;


    public void loadPurchases() {
        PurchasesServices services = new PurchasesServices();
        purchaseBox.getItems().setAll(services.getAllPurchases());
    }
    
    public void deletePurchase(){
        PurchasesServices services = new PurchasesServices();
        Purchase selectedPurchase;
        try {
            selectedPurchase = purchaseBox.getSelectionModel().getSelectedItem();
        }catch (NullPointerException e){
            Alert alert = new Alert(AlertType.WARNING, "Please select a purchase to delete");
            alert.showAndWait();
            return;
        }
        services.deletePurchase(selectedPurchase);
        loadPurchases();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPurchases();
    }

}
