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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Review;
import services.ReviewsServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLdeleteReviewController implements Initializable {

    @FXML
    private ListView<Review> reviewBox;

    private FXMLPrincipalController parent;

    public void loadReviewsList() {
        ReviewsServices reviewsServices = new ReviewsServices();
        reviewsServices.getAllReviews()
                .peek(reviews -> reviewBox.getItems().setAll(reviews))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
     }

    public void deleteReview() {
        ReviewsServices reviewsServices = new ReviewsServices();
        Review toDelete = reviewBox.getSelectionModel().getSelectedItem();
        if (toDelete != null){
            reviewsServices.deleteReview(toDelete)
                    .peek(__ -> reviewBox.getItems().remove(toDelete))
                    .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
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

    public void setParent(FXMLPrincipalController parent) {
        this.parent = parent;
    }
}
