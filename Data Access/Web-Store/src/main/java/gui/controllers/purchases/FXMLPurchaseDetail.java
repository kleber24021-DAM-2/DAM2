package gui.controllers.purchases;

import javafx.scene.control.TextField;
import model.customer.Purchase;

public class FXMLPurchaseDetail {
    public TextField purchaseDate;
    public TextField itemTxt;
    public TextField reviewTitle;
    public TextField reviewDescription;
    public TextField reviewRating;
    public TextField reviewDate;

    public void showPurchaseInfo(Purchase toShow){
        try {
            purchaseDate.setText(toShow.getDate());
            itemTxt.setText(toShow.getIdItem());
            reviewTitle.setText(toShow.getReview().getTitle());
            reviewDescription.setText(toShow.getReview().getDescription());
            reviewRating.setText(String.valueOf(Integer.valueOf(toShow.getReview().getRating())));
            reviewDate.setText(toShow.getReview().getDate());
        }catch (NullPointerException nullPointerException){
            reviewTitle.setText("N/A");
            reviewDescription.setText("N/A");
            reviewRating.setText("N/A");
            reviewDate.setText("N/A");
        }
    }
}
