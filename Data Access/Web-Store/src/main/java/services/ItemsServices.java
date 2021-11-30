/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOItems;
import dao.interfaces.DAOPurchases;
import dao.interfaces.DAOReviews;
import model.Item;
import model.Purchase;
import model.Review;

import java.util.List;

/**
 * @author dam2
 */
public class ItemsServices {

    public List<Item> getAllItems() {
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();
        return daoItems.getAll();
    }

    public Item addItem(String itemName, String itemCompany, double price) {
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();
        Item it = new Item(-1, itemName, itemCompany, price);
        return daoItems.save(it);
    }

    public int deleteItem(Item toDelete, boolean userHasConfirmed) {
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
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
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();
        daoItems.closePool();
    }

    public void updateItem(Item updatedCustomer) {
        DAOItems daoItems = DaoFactory.getInstance().getDaoItems();
        daoItems.update(updatedCustomer);
    }
}
