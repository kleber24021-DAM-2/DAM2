package gui.controllers.purchases;

import gui.controllers.FXMLPrincipalController;
import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.customer.Purchase;
import services.PurchasesServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLListPurchasesController implements Initializable {
    public DatePicker datePickerInitial;
    public DatePicker datePickerFinal;
    public Button btnFilterDate;
    public ComboBox<ListByTypesPurchases> comboListBy;
    @FXML
    private ListView<Purchase> purchaseList;
    private FXMLPrincipalController parent;

    public void chargeList() {
        PurchasesServices purchasesServices = new PurchasesServices();
        purchasesServices.getAllPurchases()
                .peek(purchases -> purchaseList.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        comboListBy.getItems().addAll(ListByTypesPurchases.values());
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }

    public void changeListBy() {
        PurchasesServices services = new PurchasesServices();
        switch (comboListBy.getSelectionModel().getSelectedItem()){
            case ITEM:
                services.getPurchasesSortedByItem()
                        .peek(purchases -> purchaseList.getItems().setAll(purchases))
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
                datePickerInitial.setVisible(false);
                datePickerFinal.setVisible(false);
                btnFilterDate.setVisible(false);
                break;
            case CUSTOMER:
                services.getPurchasesSortedByCustomer()
                        .peek(purchases -> purchaseList.getItems().setAll(purchases))
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
                datePickerInitial.setVisible(false);
                datePickerFinal.setVisible(false);
                btnFilterDate.setVisible(false);
                break;
            case DATE:
                datePickerInitial.setVisible(true);
                datePickerFinal.setVisible(true);
                btnFilterDate.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        purchaseList.setCellFactory(purchaseListView -> new ListCell<Purchase>(){
            @Override
            protected void updateItem(Purchase purchase, boolean empty) {
                super.updateItem(purchase, empty);
//                if (empty || purchase == null){
//                    setText(null);
//                }else {
//                    setText("ITEM: " + purchase.getIdItem() + " CUSTOMER: " + purchase.getCustomersByIdCustomer().getName() + " DATE: " + purchase.getDate().toString());
//                }
            }
        });
    }

    public void filterByDate() {
        LocalDate initialDate = datePickerInitial.getValue();
        LocalDate finalDate = datePickerFinal.getValue();
        PurchasesServices services = new PurchasesServices();
        if (initialDate != null && finalDate != null){
            services.getInDateRange(initialDate, finalDate)
                    .peek(purchases -> purchaseList.getItems().setAll(purchases))
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        }
    }
}
