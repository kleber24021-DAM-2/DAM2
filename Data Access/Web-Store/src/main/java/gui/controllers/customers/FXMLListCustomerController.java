package gui.controllers.customers;

import gui.controllers.UiUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.customer.Customer;
import model.customer.Purchase;
import services.CustomersServices;

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
            addressTv.setText(selectedCustomer.getAddress().getCalle());
            purchasesListView.getItems().setAll(selectedCustomer.getPurchases());
        }
    }
}
