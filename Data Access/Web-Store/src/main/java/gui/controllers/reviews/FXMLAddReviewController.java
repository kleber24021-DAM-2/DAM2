/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Customer;
import model.Purchase;
import model.Review;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLAddReviewController implements Initializable {

    @FXML
    private ListView<Customer> clientBox;
    @FXML
    private ListView<Purchase> purchaseBox;
    @FXML
    private ComboBox<String> ratingBox;
    //TODO combobox may not be String
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;
    @FXML
    private ListView<Review> reviewList;

    public void loadCustomers() {
        //Implement function to load customers of Review screen
    }

    public void loadPurchases() {
        //Implement function to load purchases of Review screen
    }

    public void addReview() {
        //Implement function to add reviews
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomers();
    }

}
