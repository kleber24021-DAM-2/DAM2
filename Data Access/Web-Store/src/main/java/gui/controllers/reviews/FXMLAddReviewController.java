/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Customer;
import model.Purchase;
import model.Review;
import services.CustomersServices;
import services.PurchasesServices;
import services.ReviewsServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLAddReviewController implements Initializable {

    @FXML
    private ListView<Purchase> purchaseBox;
    @FXML
    private ComboBox<Ratings> ratingBox;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;
    public void loadPurchases() {
        PurchasesServices purchasesServices = new PurchasesServices();
        purchaseBox.getItems().setAll(purchasesServices.getAllPurchases());
    }

    @FXML
    private void addReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        Purchase purchase = purchaseBox.getSelectionModel().getSelectedItem();
        Ratings rating = ratingBox.getSelectionModel().getSelectedItem();
        if (purchase != null && rating != null){
            Review review = new Review(-1, rating, titleBox.getText(), textBox.getText(), LocalDate.now(), purchase);
            reviewsServices.addReview(review);
            clearFields();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must choose a purchase and a rating to create a review");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        titleBox.clear();
        textBox.clear();
        purchaseBox.getSelectionModel().clearSelection();
        ratingBox.getSelectionModel().clearSelection();
    }

    private void loadRatings(){
        ratingBox.getItems().setAll(Ratings.values());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadRatings();
    }

}
