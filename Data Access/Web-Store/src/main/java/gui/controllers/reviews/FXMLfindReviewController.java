/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Item;
import model.Review;
import services.ItemsServices;
import services.ReviewsServices;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLfindReviewController implements Initializable {

    @FXML
    private ListView<Review> reviewList;
    @FXML
    private ComboBox<Item> itemBox;

    public void loadItems() {
        ItemsServices itemsServices = new ItemsServices();
        itemBox.getItems().setAll(itemsServices.getAllItems());
    }

    public void searchByItem() {
        ReviewsServices reviewsServices = new ReviewsServices();
        Item itemToSearch = itemBox.getSelectionModel().getSelectedItem();
        if (itemToSearch != null){
            List<Review> searchResult = reviewsServices.searchByItem(itemToSearch.getIdItem());
            reviewList.getItems().setAll(searchResult);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, select an item to run the search");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadItems();
    }

}
