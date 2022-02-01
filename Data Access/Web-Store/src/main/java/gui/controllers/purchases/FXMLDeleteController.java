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
import javafx.scene.control.ListView;
import model.customer.Purchase;
import services.PurchasesServices;

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
        PurchasesServices services = new PurchasesServices();
        services.getAllPurchases()
                .peek(purchases -> purchaseBox.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    public void deletePurchase() {
        PurchasesServices services = new PurchasesServices();
        Purchase selectedPurchase;
        selectedPurchase = purchaseBox.getSelectionModel().getSelectedItem();
        if (selectedPurchase == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a purchase to delete");
            alert.showAndWait();
            return;
        }
        services.deletePurchase(selectedPurchase)
                .peek(__ -> purchaseBox.getItems().remove(selectedPurchase))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
