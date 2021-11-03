/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Review;
import services.ReviewsServices;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLdeleteReviewController implements Initializable {

    @FXML
    private ListView<Review> reviewBox;

    public void loadReviewsList() {
        ReviewsServices reviewsServices = new ReviewsServices();
        reviewBox.getItems().setAll(reviewsServices.getAllReviews());
     }

    public void deleteReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        Review toDelete = reviewBox.getSelectionModel().getSelectedItem();
        if (toDelete != null){
            reviewsServices.deleteReview(toDelete);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please, select a review to delete it");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
