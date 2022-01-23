/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import gui.controllers.FXMLPrincipalController;
import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Purchase;
import model.Review;
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

    //We get the reference to the parent to get the actual user
    FXMLPrincipalController parent;

    public void loadPurchases() {
        PurchasesServices purchasesServices = new PurchasesServices();
        purchasesServices.getPurchasesByClientId(parent.getLoggedUser().getUserId())
                .peek(purchases -> purchaseBox.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    @FXML
    private void addReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        Purchase purchase = purchaseBox.getSelectionModel().getSelectedItem();
        Ratings rating = ratingBox.getSelectionModel().getSelectedItem();
        if (purchase != null && rating != null){
            Review review = new Review();
            review.setRating(rating.getValue());
            review.setTitle(titleBox.getText());
            review.setDate(LocalDate.now());
            review.setPurchasesByIdPurchases(purchase);

            reviewsServices.addReview(review)
                    .peek(__ -> {
                        UiUtils.showAlert("Added review", Alert.AlertType.INFORMATION);
                        clearFields();
                    })
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
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

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
