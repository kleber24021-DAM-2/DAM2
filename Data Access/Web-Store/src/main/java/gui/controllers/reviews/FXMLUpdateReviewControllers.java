package gui.controllers.reviews;

import gui.controllers.FXMLPrincipalController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Purchase;
import model.Review;

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
//        ReviewsServices reviewsServices = new ReviewsServices();
//        String comment = textBox.getText();
//        String title = titleBox.getText();
//        Purchase purchase = purchaseBox.getSelectionModel().getSelectedItem();
//        Ratings rating = ratingBox.getSelectionModel().getSelectedItem();
//        Review review = reviewsBox.getSelectionModel().getSelectedItem();
//
//        if (!(comment.isEmpty() || title.isEmpty())){
//            if (purchase != null && rating != null && review != null){
//                reviewsServices.updateReview(new Review(review.getIdReview(),rating.getValue(),title, comment,review.getDate(), purchase.getIdPurchase(), purchase));
//                loadAllLists();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update review");
//                alert.showAndWait();
//            }else {
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Please, set a purchase and a rating for the review");
//                alert.showAndWait();
//            }
//        }else {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Please set a title and a comment for your review");
//            alert.showAndWait();
//        }
    }

    public void loadAllLists() {
//        ReviewsServices reviewsServices = new ReviewsServices();
//        PurchasesServices purchasesServices = new PurchasesServices();
//
//        reviewsBox.getItems().setAll(reviewsServices.getReviewsByCustomer(parent.getLoggedUser().getUserId()));
//        purchaseBox.getItems().setAll(purchasesServices.getPurchasesByClientId(parent.getLoggedUser().getUserId()));
//        ratingBox.getItems().setAll(Ratings.values());
    }

    public void setParent(FXMLPrincipalController parent) {
//        this.parent = parent;
    }
}
