package gui.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FXMLAddItemController implements Initializable {

    private final ItemsServices services = new ItemsServices();

    @FXML
    private ListView<Item> itemList;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField companyBox;
    @FXML
    private TextField priceBox;

    @FXML
    private void addItem() {
        ItemsServices itemsServices = new ItemsServices();

        Pattern pattern = Pattern.compile("^\\d{1,6}(\\.\\d{1,2})?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(priceBox.getText().replace(",","."));


        if (matcher.matches()) {
            String itemName = nameBox.getText();
            String itemCompany = companyBox.getText();
            double itemPrice = Double.parseDouble(priceBox.getText().replace(",","."));

            if (!(itemName.isEmpty() ||itemCompany.isEmpty())) {
                Item addedItem = itemsServices.addItem(itemName, itemCompany, itemPrice);
                itemList.getItems().add(addedItem);
                clearAll();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name, phone and/or address can't be empty");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please, enter a valid number");
            alert.showAndWait();
        }
    }

    public void loadItemsList() {
        itemList.getItems().setAll(services.getAllItems());
    }
    private void clearAll(){
        nameBox.clear();
        companyBox.clear();
        priceBox.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemsList();
    }
}
