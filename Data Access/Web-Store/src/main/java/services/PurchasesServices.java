/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOCustomers;
import dao.mongo.DaoCustomerMongo;
import io.vavr.control.Either;
import model.customer.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public Either<String, List<Purchase>> getAllPurchases() {
        DAOCustomers dao = new DaoCustomerMongo();
        return dao.getAllPurchases();
    }

    public Either<String, List<Purchase>> getPurchasesByClientId(String clientId) {
        DAOCustomers dao = new DaoCustomerMongo();
        return dao.getPurchaseByClient(clientId);
    }

    public Either<String, Void> deletePurchase(Purchase purchase) {
        DAOCustomers daoPurchases = new DaoCustomerMongo();
        Either<String, Void> deleteResult;
        if (purchase.getReview() == null){
            deleteResult = daoPurchases.deletePurchase(purchase);
        }else {
            deleteResult = Either.left("Purchase couldn't be deleted. There are reviews associated");
        }
        return deleteResult;
     }

    public Either<String, Purchase> addPurchase(Purchase toAdd) {
        DAOCustomers daoPurchases = new DaoCustomerMongo();
       return daoPurchases.savePurchase(toAdd);
    }

    public Either<String, List<Purchase>> findByDate(LocalDate selectedDate) {
        DAOCustomers dao = new DaoCustomerMongo();
            return dao.getPurchaseByDate(selectedDate);
    }

    public Either<String, Purchase> updatePurchase(Purchase updatedPurchase) {
        DAOCustomers daoPurchases = new DaoCustomerMongo();
        return daoPurchases.updatePurchase(updatedPurchase);
    }

    public Either<String, List<Purchase>> getPurchasesSortedByItem() {
        DAOCustomers daoPurchases = new DaoCustomerMongo();
        return daoPurchases.getSortedByItem();
    }

    public Either<String, List<Purchase>> getPurchasesSortedByCustomer() {
        DAOCustomers daoPurchases = new DaoCustomerMongo();
        return daoPurchases.getSortedByCustomer();
    }

    public Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate){
        DAOCustomers daoPurchases = new DaoCustomerMongo();
        return daoPurchases.getInDateRange(initialDate, finalDate);
    }
}
