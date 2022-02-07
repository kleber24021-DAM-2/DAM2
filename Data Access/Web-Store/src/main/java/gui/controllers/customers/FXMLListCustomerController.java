package gui.controllers.customers;

import gui.controllers.UiUtils;
import gui.controllers.purchases.FXMLPurchaseDetail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import model.customer.Customer;
import model.customer.Purchase;
import services.CustomersServices;

import java.io.IOException;
import java.util.Objects;

@Log4j2
public class FXMLListCustomerController{

    @FXML
    private ListView<Purchase> purchasesListView;
    @FXML
    private TextField phoneTv;
    @FXML
    private TextField addressTv;
    @FXML
    private ListView<Customer> customersList;

    public void onStart(){
        loadLists();
        configureListViews();
    }

    private void loadLists(){
        CustomersServices services = new CustomersServices();
        services.getAllCustomers()
                .peek(customers -> customersList.getItems().setAll(customers))
                .peekLeft(error -> UiUtils.showAlert(error, Alert.AlertType.ERROR));
    }

    private void configureListViews(){
        customersList.setCellFactory(customerListView -> new ListCell<Customer>(){
            @Override
            public void updateItem(Customer customer, boolean empty){
                super.updateItem(customer, empty);
                if (empty || customer == null){
                    setText(null);
                }else {
                    setText("ID: " + customer.getId() + " NAME: " + customer.getName());
                }
            }
        });
        purchasesListView.setCellFactory(purchaseListView -> new ListCell<Purchase>(){
            @Override
            protected void updateItem(Purchase purchase, boolean empty) {
                super.updateItem(purchase, empty);
                if (empty || purchase == null){
                    setText(null);
                }else {
                    setText("ITEM: " + purchase.getIdItem() + " DATE: " + purchase.getDate().toString());
                }
            }
        });
    }

    @FXML
    private void showCustomerInfo() {
        Customer selectedCustomer = customersList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            phoneTv.setText(selectedCustomer.getTelephone());
            addressTv.setText(selectedCustomer.getAddress());
            purchasesListView.getItems().setAll(selectedCustomer.getPurchases());
        }
    }

    @FXML
    private void showPurchaseInfo() {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/purchases/FXMLModalWindow.fxml")
            );
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Purchase info");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/style/dark-theme.css")).toExternalForm()));
            stage.setScene(scene);
            FXMLPurchaseDetail controller = loader.getController();
            stage.show();
            stage.setResizable(false);
            controller.showPurchaseInfo(purchasesListView.getSelectionModel().getSelectedItem());
        }catch (IOException ioException){
            log.error(ioException.getMessage(), ioException);
        }
    }
}
