/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOCustomers;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOPurchases;
import dao.interfaces.DAOReviews;
import model.Purchase;
import model.Review;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public List<Purchase> getAllPurchases() {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
        return dao.getAll();
    }

    public List<Purchase> getPurchasesByClientId(int id) {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
        return dao.getByCustomerId(id);
    }

    public boolean deletePurchase(Purchase purchase) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();

        List<Review> reviewList = daoReviews.getByPurchaseId(purchase.getId());
        if (reviewList.isEmpty()){
            daoPurchases.delete(purchase);
            return true;
        }else {
            return false;
        }
     }

    public Purchase addPurchase(int customerId, int itemId, LocalDate date) {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
        DAOCustomers daoCustomers = DaoFactory.getInstance().getDaoCustomers();
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();

        Purchase newPurchase = new Purchase();
        newPurchase.setId(-1);
        newPurchase.setDate(date);
        newPurchase.setCustomer(daoCustomers.get(customerId));
        newPurchase.setItem(daoItems.get(itemId));

        return dao.save(newPurchase);
    }

    public List<Purchase> findByDate(LocalDate selectedDate) {
        DAOPurchases dao = DaoFactory.getInstance().getDaoPurchases();
            return dao.getByDate(selectedDate);
    }

    public void updatePurchase(Purchase updatedPurchase) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        daoPurchases.update(updatedPurchase);
    }
}
