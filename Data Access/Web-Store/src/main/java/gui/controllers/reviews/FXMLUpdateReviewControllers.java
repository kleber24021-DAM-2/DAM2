package gui.controllers.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Purchase;
import model.Review;
import services.PurchasesServices;
import services.ReviewsServices;

public class FXMLUpdateReviewControllers {

    @FXML
    private ListView<Review> reviewsBox;
    @FXML
    private TextArea textBox;
    @FXML
    private ListView<Purchase> purchaseBox;
    @FXML
    private ComboBox<Ratings> ratingBox;
    @FXML
    private TextField titleBox;

    public void updateReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        String comment = textBox.getText();
        String title = titleBox.getText();
        Purchase purchase = purchaseBox.getSelectionModel().getSelectedItem();
        Ratings rating = ratingBox.getSelectionModel().getSelectedItem();
        Review review = reviewsBox.getSelectionModel().getSelectedItem();

        if (!(comment.isEmpty() || comment.isBlank() || title.isEmpty() || title.isBlank())){
            if (purchase != null && rating != null && review != null){
                reviewsServices.updateReview(new Review(review.getIdReview(),rating,title, comment,review.getDate(), purchase));
                loadAllLists();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please, set a purchase and a rating for the review");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please set a title and a comment for your review");
            alert.showAndWait();
        }
    }

    public void loadAllLists() {
        ReviewsServices reviewsServices = new ReviewsServices();
        PurchasesServices purchasesServices = new PurchasesServices();

        reviewsBox.getItems().setAll(reviewsServices.getAllReviews());
        purchaseBox.getItems().setAll(purchasesServices.getAllPurchases());
        ratingBox.getItems().setAll(Ratings.values());
    }
}
