/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers.items;

import gui.controllers.UiUtils;
import gui.controllers.reviews.FXMLReviewInfoModalWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import model.Item;
import model.Review;
import services.ItemsServices;
import services.ReviewsServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 */
@Log4j2
public class FXMLListItemsController implements Initializable {

    private final ItemsServices itemsService = new ItemsServices();
    @FXML
    private ListView<Review> reviewListView;
    @FXML
    private ListView<Item> itemsList;
    @FXML
    private ComboBox<ListByItemsType> comboOrderBy;
    @FXML
    private TextField tfRating;
    @FXML
    private TextField tfPrice;

    public void loadItemsList() {
        itemsService.getAllItems()
                .peek(items -> itemsList.getItems().setAll(items))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
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
            controller.loadData(reviewListView.getSelectionModel().getSelectedItem());
        }catch (IOException ioException){
            log.error(ioException.getMessage(), ioException);
        }
    }

    @FXML
    private void changeOrderBy() {
        ReviewsServices services = new ReviewsServices();
        Item selectedItem = itemsList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (comboOrderBy.getSelectionModel().getSelectedItem().equals(ListByItemsType.DATE)) {
                services.getReviewsSortedByDateItem(selectedItem)
                        .peek(reviews -> reviewListView.getItems().setAll(reviews))
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
            } else {
                services.getReviewsSortedByRatingItem(selectedItem)
                        .peek(reviews -> reviewListView.getItems().setAll(reviews))
                        .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
            }
        }
    }

    @FXML
    private void showItemInfo() {
        Item selectedItem = itemsList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tfPrice.setText(Double.toString(selectedItem.getPrice()));
            double averageRating = selectedItem.getPurchasesByIdItem()
                    .stream()
                    .flatMap(purchase -> purchase.getReviewsByIdPurchase().stream())
                    .mapToInt(Review::getRating)
                    .average().orElse(-1.);
            if (averageRating == -1) {
                tfRating.setText("N/A");
            } else {
                tfRating.setText(Double.toString(averageRating));
            }
            List<Review> reviewList = selectedItem.getPurchasesByIdItem()
                    .stream()
                    .flatMap(purchase -> purchase.getReviewsByIdPurchase().stream())
                    .collect(Collectors.toList());
            reviewListView.getItems().setAll(reviewList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemsList.setCellFactory(itemListView ->
                new ListCell<Item>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText("ITEM: " + item.getName() + " BRAND: " + item.getCompany());
                        }
                    }
                });

        reviewListView.setCellFactory(reviewListView ->
                new ListCell<Review>() {
                    @Override
                    protected void updateItem(Review review, boolean empty) {
                        super.updateItem(review, empty);
                        if (empty || review == null) {
                            setText(null);
                        } else {
                            setText("TITLE: " + review.getTitle() + " RATING: " + review.getRating());
                        }
                    }
                });
        comboOrderBy.getItems().setAll(ListByItemsType.values());
    }
}
