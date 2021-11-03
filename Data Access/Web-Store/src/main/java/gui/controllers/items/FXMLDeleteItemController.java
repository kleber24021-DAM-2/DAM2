package gui.controllers.items;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Item;
import services.ItemsServices;
import services.PurchasesServices;

import java.util.Optional;

public class FXMLDeleteItemController {

    public void loadItemsList(){
        ItemsServices service = new ItemsServices();
        itemBox.getItems().setAll(service.getAllItems());
    }

    @FXML
    private ListView<Item> itemBox = new ListView<>();


    @FXML
    private void deleteItem() {
        ItemsServices itemsServices = new ItemsServices();
        PurchasesServices purchasesService = new PurchasesServices();
        Item selectedItem = itemBox.getSelectionModel().getSelectedItem();
        try {
            if (!itemsServices.deleteItem(selectedItem, false)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are purchases with this item. Would you like to delete them to?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get()==ButtonType.OK){
                    purchasesService.deletePurchasesByItemID(selectedItem.getIdItem());
                    itemsServices.deleteItem(selectedItem, true);
                }
            }
            loadItemsList();
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, select an object to delete");
            alert.showAndWait();
        }
    }
}
