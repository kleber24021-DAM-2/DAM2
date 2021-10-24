/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.dao_implementations.IoDAOItems;
import dao.dao_implementations.NioDAOPurchases;
import model.Item;

import java.util.List;

/**
 * @author dam2
 */
public class ItemsServices {

    public List<Item> getAllItems() {
        IoDAOItems daoItems = new IoDAOItems();
        return daoItems.getAll();
    }

    public boolean addItem(int itemId, String itemName, String itemCompany, double price) {
        IoDAOItems daoItems = new IoDAOItems();
        Item it = new Item(itemId, itemName, itemCompany, price);
        return daoItems.save(it);
    }

    public boolean deleteItem(Item toDelete) {
        NioDAOPurchases daoPurchases = new NioDAOPurchases();
        IoDAOItems daoItems = new IoDAOItems();
        if (daoPurchases.get(toDelete.getIdItem()) == null){
            return daoItems.delete(toDelete);
        }
        return false;
    }
}
