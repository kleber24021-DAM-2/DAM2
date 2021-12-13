/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.dao_implementations.*;
import dao.interfaces.*;
import model.Purchase;
import model.Review;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    DAOItems daoItems = new DaoItemsHibernate();
    DAOCustomers daoCustomers = new DaoCustomersHibernate();
    DAOReviews daoReviews = new DaoReviewsHibernate();
    DAOPurchases daoPurchases = new DaoPurchaseHibernate();
    DAOUsers daoUsers = new DaoUserHibernate();

    public List<Purchase> getAllPurchases() {
        return daoPurchases.getAll();
    }

    public List<Purchase> getPurchasesByClientId(int id) {
        return daoPurchases.getByCustomerId(id);
    }

    public boolean deletePurchase(Purchase purchase) {
        List<Review> reviewList = daoReviews.getByPurchaseId(purchase.getId());
        if (reviewList.isEmpty()){
            daoPurchases.delete(purchase);
            return true;
        }else {
            return false;
        }
     }

    public Purchase addPurchase(int customerId, int itemId, LocalDate date) {
        Purchase newPurchase = new Purchase();
        newPurchase.setId(-1);
        newPurchase.setDate(date);
        newPurchase.setCustomer(daoCustomers.get(customerId));
        newPurchase.setItem(daoItems.get(itemId));

        return daoPurchases.save(newPurchase);
    }

    public List<Purchase> findByDate(LocalDate selectedDate) {
            return daoPurchases.getByDate(selectedDate);
    }

    public void updatePurchase(Purchase updatedPurchase) {
        daoPurchases.update(updatedPurchase);
    }
}
