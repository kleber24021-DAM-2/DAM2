/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactoryItems;
import dao.daofactories.DaoFactoryPurchases;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOPurchases;
import model.Customer;
import model.Item;
import model.Purchase;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dam2
 */
public class ItemsServices {

    public List<Item> getAllItems() {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        return daoItems.getAll();
    }

    public boolean addItem(String itemName, String itemCompany, double price) {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        Item it = new Item(-1, itemName, itemCompany, price);
        return daoItems.save(it);
    }

    public boolean deleteItem(Item toDelete, boolean userHasConfirmed) {
        DAOPurchases daoPurchases = new DaoFactoryPurchases().getDaoPurchases();
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();

        List<Purchase> purchaseList = daoPurchases.getAll();
        purchaseList = purchaseList.stream()
                .filter(p -> p.getItem().getIdItem() == toDelete.getIdItem())
                .collect(Collectors.toList());
        if (!purchaseList.isEmpty() && !userHasConfirmed){
            return false;
        }
        return daoItems.delete(toDelete);
    }

    public void onCloseApplication() {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        daoItems.closePool();
    }

    public void updateItem(Item updatedCustomer) {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        daoItems.update(updatedCustomer);
    }
}
