/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.hibernatemodels.EntityPurchases;
import services.PurchasesServices;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLDatePurchasesController implements Initializable {

    @FXML
    private DatePicker dateBox;
    @FXML
    private ListView<EntityPurchases> purchaseList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        List<EntityPurchases> list = services.findByDate(selectedDate);
        if (list.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No purchases were realized on " + selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            alert.showAndWait();
        }else {
            purchaseList.getItems().setAll(list);
        }
    }
}
