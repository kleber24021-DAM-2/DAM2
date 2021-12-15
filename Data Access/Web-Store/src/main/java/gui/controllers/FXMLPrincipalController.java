/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controllers;

import gui.controllers.customers.*;
import gui.controllers.items.FXMLAddItemController;
import gui.controllers.items.FXMLDeleteItemController;
import gui.controllers.items.FXMLListItemsController;
import gui.controllers.items.FXMLUpdateItemController;
import gui.controllers.purchases.*;
import gui.controllers.reviews.FXMLAddReviewController;
import gui.controllers.reviews.FXMLUpdateReviewControllers;
import gui.controllers.reviews.FXMLdeleteReviewController;
import gui.controllers.reviews.FXMLfindReviewController;
import gui.controllers.users.FXMLUpdatePassword;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.user.SafeUser;
import services.ItemsServices;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLPrincipalController implements Initializable {

    //Vista del Admin
    @FXML
    private MenuItem menuItemAdd;
    @FXML
    private MenuItem menuItemList;
    @FXML
    private MenuItem menuItemFind;
    @FXML
    private MenuItem menuItemDelete;
    @FXML
    private MenuItem menuItemUpdate;
    @FXML
    private MenuItem menuCustomerAdd;
    @FXML
    private MenuItem menuCustomerList;
    @FXML
    private MenuItem menuCustomerFind;
    @FXML
    private MenuItem menuCustomerDelete;
    @FXML
    private MenuItem menuPurchaseAdd;
    @FXML
    private MenuItem menuPurchaseFindDate;
    @FXML
    private MenuItem menuPurchaseDelete;
    @FXML
    private MenuItem menuPurchaseUpdate;
    @FXML
    private MenuItem menuReviewDelete;
    //Vista de Customer
    @FXML
    private MenuItem menuReviewAdd;
    @FXML
    private MenuItem menuReviewUpdate;
    //Vista mixta
    @FXML
    private MenuItem menuCustomerUpdate;
    @FXML
    private MenuItem menuReviewFind;
    //Reference to the top menu to change its visibility when needed.
    @FXML
    private BorderPane fxRoot;
    @FXML
    private MenuBar fxMenuTop;
    // Get y set of the user to use it wherever we need it
    private SafeUser loggedUser;
    // References to other panes to load them and access their controllers
    private AnchorPane login;
    private FXMLLoginController loginController;
    private AnchorPane welcome;
    private FXMLWelcomeController welcomeController;
    private AnchorPane purchases;
    private FXMLAddPurchasesController purchasesController;
    private AnchorPane datePurchases;
    private FXMLDatePurchasesController datePurchasesController;
    private AnchorPane delete;
    private FXMLDeleteController deleteController;
    private AnchorPane addCustomer;
    private FXMLAddCustomerController addCustomerController;
    private AnchorPane findCustomer;
    private FXMLfindCustomerController findCustomerController;
    private AnchorPane deleteCustomer;
    private FXMLdeleteCustomerController deleteCustomerController;
    private AnchorPane addReview;
    private FXMLAddReviewController addReviewController;
    private AnchorPane findReview;
    private FXMLfindReviewController findReviewController;
    private AnchorPane deleteReview;
    private FXMLdeleteReviewController deleteReviewController;
    private AnchorPane listItems;
    private FXMLListItemsController listItemsController;
    private AnchorPane addItems;
    private FXMLAddItemController addItemController;
    private AnchorPane deleteItems;
    private FXMLDeleteItemController deleteItemsController;
    private AnchorPane updateItems;

    private FXMLUpdateItemController updateItemController;

    private FXMLUpdatePurchasesController updatePurchasesController;
    private AnchorPane updatePurchasePane;
    private FXMLUpdateCostumerController updateCostumerController;
    private AnchorPane updateCustomerPane;
    private FXMLUpdateReviewControllers updateReviewControllers;
    private AnchorPane updateReviewPane;

    private FXMLListCustomerController listCustomerController;
    private AnchorPane listCustomerPane;

    private FXMLUpdatePassword updatePasswordController;
    private AnchorPane updatePasswordPane;

    private AnchorPane updateCustomerUserPane;
    private FXMLUpdateCostumerAsUserController updateCostumerUserController;

    private AnchorPane listPurchasesPane;
    private FXMLListPurchasesController listPurchasesController;

    public void setFxRoot(BorderPane fxRoot) {
        this.fxRoot = fxRoot;
    }

    public SafeUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(SafeUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void preloadLogin() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/users/FXMLLogin.fxml"));
            login = loaderMenu.load();
            loginController
                    = loaderMenu.getController();

            loginController.setPrincipal(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadWelcome() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/FXMLWelcome.fxml"));
            welcome = loaderMenu.load();
            welcomeController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadPurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLAddPurchases.fxml"));
            purchases = loaderMenu.load();
            purchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDatePurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDatePurchases.fxml"));
            datePurchases = loaderMenu.load();
            datePurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDelete() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDelete.fxml"));
            delete = loaderMenu.load();
            deleteController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public void preloadAddCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLAddCustomer.fxml"));
            addCustomer = loaderMenu.load();
            addCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadFindCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLfindCustomer.fxml"));
            findCustomer = loaderMenu.load();
            findCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadDeleteCustomer() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLdeleteCustomer.fxml"));
            deleteCustomer = loaderMenu.load();
            deleteCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadAddReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLaddReview.fxml"));
            addReview = loaderMenu.load();
            addReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadFindReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLfindReview.fxml"));
            findReview = loaderMenu.load();
            findReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadDeleteReview() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLdeleteReview.fxml"));
            deleteReview = loaderMenu.load();
            deleteReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadAddItems() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLAddItem.fxml"));
            addItems = loaderMenu.load();
            addItemController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdateItem() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLUpdateItem.fxml"));
            updateItems = loaderMenu.load();
            updateItemController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadListItems() {

        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLListItems.fxml"));
            listItems = loaderMenu.load();
            listItemsController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDeleteItems() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource("/fxml/items/FXMLdeleteItem.fxml"));
            deleteItems = loaderMenu.load();
            deleteItemsController = loaderMenu.getController();
        } catch (IOException io) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, io);
        }
    }

    public void preloadUpdateCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLUpdateCustomerAdmin.fxml"));
            updateCustomerPane = loaderMenu.load();
            updateCostumerController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdateCustomerAsUser(){
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLUpdateCustomerUser.fxml"));
            updateCustomerUserPane = loaderMenu.load();
            updateCostumerUserController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdatePurchase() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLUpdatePurchases.fxml"));
            updatePurchasePane = loaderMenu.load();
            updatePurchasesController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdateReviews() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLUpdateReview.fxml"));
            updateReviewPane = loaderMenu.load();
            updateReviewControllers = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadListCustomer(){
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource("/fxml/customers/FXMLListCustomers.fxml")
            );
            listCustomerPane = loaderMenu.load();
            listCustomerController = loaderMenu.getController();
        }catch (IOException ex){
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdatePassword(){
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource("/fxml/users/FXMLUpdatePassword.fxml")
            );
            updatePasswordPane = loaderMenu.load();
            updatePasswordController = loaderMenu.getController();
        }catch (IOException ex){
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    public void preloadListPurchase(){
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource("/fxml/purchases/FXMLListPurchases.fxml")
            );
            listPurchasesPane = loaderMenu.load();
            listPurchasesController = loaderMenu.getController();
        }catch (IOException ex){
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chargeListPurchase(){
        listPurchasesController.setParent(this);
        listPurchasesController.chargeList();
        fxRoot.setCenter(listPurchasesPane);
    }

    public void chargeLogin() {
        loggedUser = null;
        setForAdmin(false);
        setForCustomer(false);
        fxRoot.setCenter(login);
        fxMenuTop.setVisible(false);
    }

    public void chargeWelcome() {
        welcomeController.setLogin(this.getLoggedUser());
        fxMenuTop.setVisible(true);
        fxRoot.setCenter(welcome);
    }

    public void chargePurchases() {
        purchasesController.load();
        fxRoot.setCenter(purchases);
    }

    public void chargeDatePurchases() {
        fxRoot.setCenter(datePurchases);
    }

    public void chargeDelete() {
        deleteController.loadPurchases();
        fxRoot.setCenter(delete);
    }

    public void chargeAddCustomer() {
        addCustomerController.loadCustomersList();
        fxRoot.setCenter(addCustomer);
    }

    public void chargeFindCustomer() {
        fxRoot.setCenter(findCustomer);
    }

    public void chargeDeleteCustomer() {
        deleteCustomerController.loadCustomersList();
        fxRoot.setCenter(deleteCustomer);
    }

    public void chargeAddReview() {
        addReviewController.setParent(this);
        addReviewController.loadPurchases();
        fxRoot.setCenter(addReview);
    }

    public void chargeDeleteReview() {
        deleteReviewController.setParent(this);
        deleteReviewController.loadReviewsList();
        fxRoot.setCenter(deleteReview);
    }

    public void chargeFindReview() {
        findReviewController.loadItems();
        fxRoot.setCenter(findReview);
    }

    public void listItems() {
        listItemsController.loadItemsList();
        fxRoot.setCenter(listItems);
    }

    public void addItems() {
        addItemController.loadItemsList();
        fxRoot.setCenter(addItems);
    }

    public void updateItems() {
        updateItemController.loadItemList();
        fxRoot.setCenter(updateItems);
    }

    public void deleteItems() {
        deleteItemsController.loadItemsList();
        fxRoot.setCenter(deleteItems);
    }

    public void showUpdateCustomers() {
        if (getLoggedUser().getId() > 0){
            updateCostumerUserController.setParent(this);
            updateCostumerUserController.loadUserInfo();
            fxRoot.setCenter(updateCustomerUserPane);
        }else {
            updateCostumerController.loadAllLists();
            fxRoot.setCenter(updateCustomerPane);
        }
    }

    public void showUpdatePurchases() {
        updatePurchasesController.loadAllLists();
        fxRoot.setCenter(updatePurchasePane);
    }

    public void showUpdateReviews() {
        updateReviewControllers.setParent(this);
        updateReviewControllers.loadAllLists();
        fxRoot.setCenter(updateReviewPane);
    }

    public void chargePasswordUpdate() {
        updatePasswordController.setParent(this);
        fxRoot.setCenter(updatePasswordPane);
    }

    public void chargeListCustomer() {
        listCustomerController.loadList();
        fxRoot.setCenter(listCustomerPane);
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preloadWelcome();
        preloadLogin();

        preloadPurchases();
        preloadDatePurchases();
        preloadDelete();

        preloadAddCustomer();
        preloadFindCustomer();
        preloadDeleteCustomer();
        preloadListCustomer();

        preloadAddReview();
        preloadDeleteReview();
        preloadFindReview();

        preloadListItems();
        preloadAddItems();
        preloadDeleteItems();
        preloadUpdateItem();
        preloadUpdateCustomer();
        preloadUpdatePurchase();
        preloadUpdateReviews();
        preloadUpdatePassword();
        preloadUpdateCustomerAsUser();
        preloadListPurchase();

        chargeLogin();

    }


    public void closeApplication(WindowEvent event) {
        ItemsServices services = new ItemsServices();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the application?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent()) {
            if (answer.get().equals(ButtonType.OK)) {
                services.onCloseApplication();
            } else if (answer.get().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        }
    }

    @FXML
    private void closeWindow() {
        Window window = fxRoot.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setForCustomer(boolean isCustomer){
        menuItemAdd.setDisable(isCustomer);
        menuItemList.setDisable(isCustomer);
        menuItemFind.setDisable(isCustomer);
        menuItemDelete.setDisable(isCustomer);
        menuItemUpdate.setDisable(isCustomer);
        menuCustomerAdd.setDisable(isCustomer);
        menuCustomerList.setDisable(isCustomer);
        menuCustomerFind.setDisable(isCustomer);
        menuCustomerDelete.setDisable(isCustomer);
        menuPurchaseAdd.setDisable(isCustomer);
        menuPurchaseFindDate.setDisable(isCustomer);
        menuPurchaseDelete.setDisable(isCustomer);
        menuPurchaseUpdate.setDisable(isCustomer);
        menuReviewDelete.setDisable(isCustomer);
        //TODO
    }

    public void setForAdmin(boolean isAdmin){
        menuReviewAdd.setDisable(isAdmin);
        menuReviewUpdate.setDisable(isAdmin);
    }

}
