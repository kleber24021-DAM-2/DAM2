package gui.controllers.reviews;

import gui.controllers.FXMLPrincipalController;
import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.customer.Purchase;
import model.item.Review;
import services.PurchasesServices;
import services.ReviewsServices;

import java.time.LocalDate;

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

    private FXMLPrincipalController parent;

    public void updateReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        String comment = textBox.getText();
        String title = titleBox.getText();
        Purchase purchase = purchaseBox.getSelectionModel().getSelectedItem();
        Ratings rating = ratingBox.getSelectionModel().getSelectedItem();
        Review toUpdateReview = reviewsBox.getSelectionModel().getSelectedItem();
        int selectedReviewIndex = reviewsBox.getSelectionModel().getSelectedIndex();

        if (!(comment.isEmpty() || title.isEmpty())) {
            if (purchase != null && rating != null && toUpdateReview != null) {
                toUpdateReview.setRating(rating.getValue());
                toUpdateReview.setTitle(title);
                toUpdateReview.setDescription(comment);
                toUpdateReview.setDate(LocalDate.now().toString());
//                toUpdateReview.setPurchasesByIdPurchases(purchase);
                reviewsServices.updateReview(toUpdateReview)
                        .peek(updatedReview -> {
                            reviewsBox.getItems().remove(selectedReviewIndex);
                            reviewsBox.getItems().add(selectedReviewIndex, updatedReview);
                            UiUtils.showAlert("Updated review", Alert.AlertType.INFORMATION);
                        })
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please, set a purchase and a rating for the review");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please set a title and a comment for your review");
            alert.showAndWait();
        }
    }

    public void loadAllLists() {
        ReviewsServices reviewsServices = new ReviewsServices();
        PurchasesServices purchasesServices = new PurchasesServices();

        reviewsServices.getReviewsByCustomer(parent.getLoggedUser().getId())
                .peek(reviews -> reviewsBox.getItems().setAll(reviews))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        purchasesServices.getPurchasesByClientId(parent.getLoggedUser().getId())
                .peek(purchases -> purchaseBox.getItems().setAll(purchases))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
        ratingBox.getItems().setAll(Ratings.values());
    }

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
