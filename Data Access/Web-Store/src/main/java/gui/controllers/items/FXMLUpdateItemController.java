package gui.controllers.items;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.item.Item;
import services.ItemsServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FXMLUpdateItemController {

    @FXML
    private TextField priceBox;
    @FXML
    private TextField companyBox;
    @FXML
    private TextField nameBox;
    @FXML
    private ListView<Item> itemList;


    @FXML
    private void updateItem() {
        Pattern pattern = Pattern.compile("^\\d{1,6}(\\.\\d{1,2})?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(priceBox.getText());
        ItemsServices itemsServices = new ItemsServices();
        Item toUpdate = itemList.getSelectionModel().getSelectedItem();
        int toUpdateIndex = itemList.getItems().indexOf(toUpdate);
        if (matcher.matches()) {
            if (toUpdate != null) {
                if (!(nameBox.getText().isEmpty() || companyBox.getText().isEmpty() || priceBox.getText().isEmpty())) {
                    toUpdate.setName(nameBox.getText());
                    toUpdate.setCompany(companyBox.getText());
                    toUpdate.setPrice(Double.parseDouble(priceBox.getText()));
                    itemsServices.updateItem(toUpdate)
                            .peek(updatedItem -> {
                                itemList.getItems().remove(toUpdateIndex);
                                itemList.getItems().add(toUpdateIndex, updatedItem);
                            })
                            .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Name, phone and/or address can't be empty");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, set the price as a number with up to two decimals");
            alert.showAndWait();
        }
    }

    public void loadItemList() {
        ItemsServices itemsServices = new ItemsServices();
        itemsServices.getAllItems()
                .peek(items -> itemList.getItems().setAll(items))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }


    @FXML
    private void setItemInfo() {
        Item selectedItem = itemList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            nameBox.setText(selectedItem.getName());
            companyBox.setText(selectedItem.getCompany());
            priceBox.setText(String.valueOf(selectedItem.getPrice()));
        }
    }
}
