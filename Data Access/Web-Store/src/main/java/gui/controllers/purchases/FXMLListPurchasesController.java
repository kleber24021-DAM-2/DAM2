package gui.controllers.purchases;

import gui.controllers.FXMLPrincipalController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Purchase;
import services.PurchasesServices;

import java.util.List;

public class FXMLListPurchasesController {
    @FXML
    private ListView<Purchase> purchaseList;
    private FXMLPrincipalController parent;

    public void chargeList() {
        PurchasesServices purchasesServices = new PurchasesServices();
        int loggedUserId = parent.getLoggedUser().getId();
        List<Purchase> purchases;
        if (loggedUserId <= 0) {
            purchases = purchasesServices.getAllPurchases();
        }else {
            purchases = purchasesServices.getPurchasesByClientId(loggedUserId);
        }
        purchaseList.getItems().setAll(purchases);
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
