/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.reviews;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.Item;
import model.Review;
import services.CustomersServices;
import services.ItemsServices;
import services.ReviewsServices;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Log4j2
public class FXMLfindReviewController implements Initializable {


    @FXML
    private ListView<Review> lvReviews;
    @FXML
    private ComboBox<ListByReviews> comboListBy;
    @FXML
    private ComboBox<Item> comboItem;
    @FXML
    private ComboBox<Customer> comboCustomer;
    @FXML
    private ComboBox<Ratings> comboRating;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboListBy.getItems().setAll(ListByReviews.values());
        comboRating.getItems().setAll(Ratings.values());

        lvReviews.setCellFactory(reviewListView ->
                new ListCell<Review>() {
                    @Override
                    protected void updateItem(Review review, boolean empty) {
                        super.updateItem(review, empty);
                        if (empty || review == null) {
                            setText(null);
                        } else {
                            setText("ITEM: " + review.getPurchasesByIdPurchases().getItemsByIdItem().getName() + " CUSTOMER: " + review.getPurchasesByIdPurchases().getCustomersByIdCustomer().getName() + " RATING: " + review.getRating());
                        }
                    }
                }
        );
        comboItem.setCellFactory(comboBoxItem ->
                new ListCell<Item>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText("ITEM: " + item.getName());
                        }
                    }
                });
        comboCustomer.setCellFactory(comboBoxCustomer ->
                new ListCell<Customer>(){
                    @Override
                    protected void updateItem(Customer customer, boolean empty) {
                        super.updateItem(customer, empty);
                        if (empty || customer == null){
                            setText(null);
                        }else {
                            setText("CUSTOMER: " + customer.getName());
                        }
                    }
                });
    }

    @FXML
    private void showReviewInfo() {
        try {
            FXMLLoader loaderNewWindow = new FXMLLoader(
                    getClass().getResource("/fxml/reviews/FXMLReviewInfoModalWindow.fxml"));
            AnchorPane root = loaderNewWindow.load();
            Stage stage = new Stage();
            stage.setTitle("Review info");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/style/dark-theme.css")).toExternalForm()));
            stage.setScene(scene);
            FXMLReviewInfoModalWindow controller = loaderNewWindow.getController();
            stage.show();
            stage.setResizable(false);
            controller.loadData(lvReviews.getSelectionModel().getSelectedItem());
        }catch (IOException ioException){
            log.error(ioException.getMessage(), ioException);
        }
    }

    @FXML
    private void changeListBy() {
        switch (comboListBy.getSelectionModel().getSelectedItem()){
            case CUSTOMER:
                comboCustomer.setVisible(true);
                comboItem.setVisible(false);
                comboRating.setVisible(false);
                break;
            case ITEM:
                comboCustomer.setVisible(false);
                comboItem.setVisible(true);
                comboRating.setVisible(false);
                break;
            case RATING:
                comboCustomer.setVisible(false);
                comboItem.setVisible(false);
                comboRating.setVisible(true);
                break;
        }
    }

    @FXML
    private void changeComboItem() {
        ReviewsServices services = new ReviewsServices();
        Item selectedItem = comboItem.getSelectionModel().getSelectedItem();
        services.getReviewsByItem(selectedItem)
                .peek(reviews -> lvReviews.getItems().setAll(reviews))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    @FXML
    private void changeComboCustomer() {
        ReviewsServices services = new ReviewsServices();
        Customer selectedCustomer = comboCustomer.getSelectionModel().getSelectedItem();
        services.getReviewsByCustomer(selectedCustomer.getIdCustomer())
                .peek(reviews -> lvReviews.getItems().setAll(reviews))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    @FXML
    private void changeComboRating() {
        ReviewsServices services = new ReviewsServices();
        Ratings selectedRating = comboRating.getSelectionModel().getSelectedItem();
        services.getReviewsByRating(selectedRating)
                .peek(reviews -> lvReviews.getItems().setAll(reviews))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    public void loadItems(){
        ItemsServices itemsServices = new ItemsServices();
        CustomersServices customersServices = new CustomersServices();

        itemsServices.getAllItems()
                .peek(items -> comboItem.getItems().setAll(items))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));

        customersServices.getAllCustomers()
                .peek(customers -> comboCustomer.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }
}
