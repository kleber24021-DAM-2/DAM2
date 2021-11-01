/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.purchases;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Purchase;
import services.PurchasesServices;


/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLDatePurchasesController implements Initializable {

    @FXML
    private DatePicker dateBox;
    @FXML
    private ListView<Purchase> purchaseList;

    public void loadPurchasesList() {
        PurchasesServices services = new PurchasesServices();
        purchaseList.getItems().setAll(services.getAllPurchases());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Not needed. Imposed by exercise.
    }
    @FXML
    private void searchByDate() {
        LocalDate selectedDate;
        PurchasesServices services = new PurchasesServices();
        try {
            selectedDate = dateBox.getValue();
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a valid date");
            alert.showAndWait();
            return;
        }
        List<Purchase> list = services.findByDate(selectedDate);
        purchaseList.getItems().setAll(list);
    }
}
