package gui.controllers.items;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.DeleteItemResults;
import model.item.Item;
import services.ItemsServices;

import java.util.Optional;

public class FXMLDeleteItemController {

    public void loadItemsList(){
        ItemsServices service = new ItemsServices();
        service.getAllItems()
                .peek(items -> itemBox.getItems().setAll(items))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    @FXML
    private ListView<Item> itemBox = new ListView<>();


    @FXML
    private void deleteItem() {
        ItemsServices itemsServices = new ItemsServices();
        Item selectedItem = itemBox.getSelectionModel().getSelectedItem();
        try {
            itemsServices.deleteItem(selectedItem, false)
                    .peek(__ -> itemBox.getItems().remove(selectedItem))
                    .peekLeft(deleteItemResults -> {
                        if (deleteItemResults.equals(DeleteItemResults.ASSOCIATED_PURCHASES)){
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are purchases with this item. Would you like to delete them to?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get()==ButtonType.OK){
                                itemsServices.deleteItem(selectedItem, true);
                            }
                        }else if (deleteItemResults.equals(DeleteItemResults.ASSOCIATED_REVIEWS)){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "There are reviews associated to this item. This item can't be deleted");
                            alert.showAndWait();
                        }else {
                            UiUtils.showAlert("There was an internal error. Please try again.", Alert.AlertType.ERROR);
                        }
                    });
            loadItemsList();
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, select an object to delete");
            alert.showAndWait();
        }
    }
}
