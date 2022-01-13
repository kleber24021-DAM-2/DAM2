package gui.controllers.items;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Item;
import services.ItemsServices;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Item selectedItem = itemBox.getSelectionModel().getSelectedItem();
        try {
            int deleteAnswer = itemsServices.deleteItem(selectedItem, false);
            if (deleteAnswer == -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are purchases with this item. Would you like to delete them to?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get()==ButtonType.OK){
                    itemsServices.deleteItem(selectedItem, true);
                }
            }else if (deleteAnswer == -2){
                Alert alert = new Alert(Alert.AlertType.WARNING, "There are reviews associated to this item. This item can't be deleted");
                alert.showAndWait();
            }
            loadItemsList();
        }catch (NullPointerException exception){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage(), exception);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, select an object to delete");
            alert.showAndWait();
        }
    }
}
