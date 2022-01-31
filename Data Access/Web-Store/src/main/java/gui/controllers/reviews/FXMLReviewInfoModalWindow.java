package gui.controllers.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.item.Review;

import java.time.LocalDate;

public class FXMLReviewInfoModalWindow {
    @FXML
    private TextField tfRating;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea tfComment;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea tfPurchase;
    @FXML
    private
    TextField tfId;

    public void loadData(Review selectedReview) {
        tfId.setText(Integer.toString(selectedReview.getIdReview()));
        tfRating.setText(Integer.toString(selectedReview.getRating()));
        tfTitle.setText(selectedReview.getTitle());
        tfComment.setText(selectedReview.getDescription());
        datePicker.setValue(LocalDate.parse(selectedReview.getDate()));
//        tfPurchase.setText(selectedReview.getPurchasesByIdPurchases().toString());
    }
}
