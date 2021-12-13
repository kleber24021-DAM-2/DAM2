/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.dao_implementations.*;
import dao.interfaces.*;
import model.Item;
import model.Purchase;
import model.Review;

import java.util.List;

/**
 * @author dam2
 */
public class ItemsServices {

    DAOItems daoItems = new DaoItemsHibernate();
    DAOCustomers daoCustomers = new DaoCustomersHibernate();
    DAOReviews daoReviews = new DaoReviewsHibernate();
    DAOPurchases daoPurchases = new DaoPurchaseHibernate();
    DAOUsers daoUsers = new DaoUserHibernate();

    public List<Item> getAllItems() {
        return daoItems.getAll();
    }

    public Item addItem(String itemName, String itemCompany, double price) {
        Item it = new Item(-1, itemName, itemCompany, price);
        return daoItems.save(it);
    }

    public int deleteItem(Item toDelete, boolean userHasConfirmed) {
        int returnMessage= -3;

        List<Review> reviewList = daoReviews.getByItemId(toDelete.getIdItem());

        if (reviewList.isEmpty()){
            List<Purchase> purchaseList = daoPurchases.getByItemId(toDelete.getIdItem());
            if (!purchaseList.isEmpty() && !userHasConfirmed){
                returnMessage = -1;
            }else {
                if (daoItems.delete(toDelete)){
                    returnMessage = 0;
                }
            }
        }else {
            returnMessage = -2;
        }
        return returnMessage;
    }

    public void onCloseApplication() {
        daoItems.closePool();
    }

    public void updateItem(Item updatedCustomer) {
        daoItems.update(updatedCustomer);
    }
}
