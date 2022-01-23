/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOPurchases;
import io.vavr.control.Either;
import model.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public Either<String, List<Purchase>> getAllPurchases() {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
        return dao.getAll();
    }

    public Either<String, List<Purchase>> getPurchasesByClientId(int id) {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
        return dao.getByCustomerId(id);
    }

    public Either<String, Void> deletePurchase(Purchase purchase) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        Either<String, Void> deleteResult;
        if (purchase.getReviewsByIdPurchase().isEmpty()){
            deleteResult = daoPurchases.delete(purchase);
        }else {
            deleteResult = Either.left("Purchase couldn't be deleted. There are reviews associated");
        }
        return deleteResult;
     }

    public Either<String, Purchase> addPurchase(Purchase toAdd) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
       return daoPurchases.save(toAdd);
    }

    public Either<String, List<Purchase>> findByDate(LocalDate selectedDate) {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
            return dao.getByDate(selectedDate);
    }

    public Either<String, Purchase> updatePurchase(Purchase updatedPurchase) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        return daoPurchases.update(updatedPurchase);
    }

    public Either<String, List<Purchase>> getPurchasesSortedByItem() {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        return daoPurchases.getSortedByItem();
    }

    public Either<String, List<Purchase>> getPurchasesSortedByCustomer() {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        return daoPurchases.getSortedByCustomer();
    }

    public Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate){
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        return daoPurchases.getInDateRange(initialDate, finalDate);
    }
}
