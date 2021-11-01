package gui.controllers.items;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAddItemController implements Initializable {

    private ItemsServices services = new ItemsServices();

    @FXML
    private ListView<Item> itemList;
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField companyBox;
    @FXML
    private TextField priceBox;

    @FXML
    private void addItem(ActionEvent actionEvent) {
        try {
            int itemId = Integer.parseInt(idBox.getText());
            String itemName = nameBox.getText();
            String itemCompany = companyBox.getText();
            float itemPrice = Float.parseFloat(priceBox.getText());
            if (services.addItem(itemId, itemName, itemCompany, itemPrice)){
                loadItemsList();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Item hasn't been uploaded. Check id");
                alert.showAndWait();
            }

        }catch (NumberFormatException numEx){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Check the price. It must be a float");
            alert.showAndWait();
        }
    }

    public void loadItemsList() {
        itemList.getItems().setAll(services.getAllItems());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemsList();
    }
}
