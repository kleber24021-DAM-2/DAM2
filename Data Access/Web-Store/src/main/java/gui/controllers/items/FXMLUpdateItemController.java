package gui.controllers.items;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;

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
//        Pattern pattern = Pattern.compile("^\\d{1,6}(\\.\\d{1,2})?$", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(priceBox.getText());
//        ItemsServices itemsServices = new ItemsServices();
//        Item toUpdate = itemList.getSelectionModel().getSelectedItem();
//        if (matcher.matches()) {
//            if (toUpdate != null) {
//                if (!(nameBox.getText().isEmpty() || companyBox.getText().isEmpty() || priceBox.getText().isEmpty())) {
//                    Item updatedItem = new Item(toUpdate.getIdItem(), nameBox.getText(), companyBox.getText(), Double.parseDouble(priceBox.getText()));
//                    itemsServices.updateItem(updatedItem);
//                    loadItemList();
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.WARNING, "Name, phone and/or address can't be empty");
//                    alert.showAndWait();
//                }
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, set the price as a number with up to two decimals");
//            alert.showAndWait();
//        }
    }

    public void loadItemList() {
//        ItemsServices itemsServices = new ItemsServices();
//        itemList.getItems().setAll(itemsServices.getAllItems());
    }

    @FXML
    private void setItemInfo() {
//        Item selectedItem = itemList.getSelectionModel().getSelectedItem();
//        if (selectedItem != null){
//            nameBox.setText(selectedItem.getName());
//            companyBox.setText(selectedItem.getCompany());
//            priceBox.setText(String.valueOf(selectedItem.getPrice()));
//        }
    }
}
