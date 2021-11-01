/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactoryItems;
import dao.dao_implementations.file.NioDAOPurchases;
import dao.daofactories.DaoFactoryPurchases;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOPurchases;
import model.Item;

import java.util.List;

/**
 * @author dam2
 */
public class ItemsServices {

    public List<Item> getAllItems() {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        return daoItems.getAll();
    }

    public boolean addItem(int itemId, String itemName, String itemCompany, double price) {
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        Item it = new Item(itemId, itemName, itemCompany, price);
        return daoItems.save(it);
    }

    public boolean deleteItem(Item toDelete) {
        DAOPurchases daoPurchases = new DaoFactoryPurchases().getDaoPurchases();
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();
        if (daoPurchases.get(toDelete.getIdItem()) == null){
            return daoItems.delete(toDelete);
        }
        return false;
    }
}
