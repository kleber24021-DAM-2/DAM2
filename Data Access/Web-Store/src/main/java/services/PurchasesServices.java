/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.daofactories.DaoFactoryCustomers;
import dao.daofactories.DaoFactoryItems;
import dao.daofactories.DaoFactoryPurchases;
import dao.interfaces.DAOCustomers;
import dao.interfaces.DAOItems;
import dao.interfaces.DAOPurchases;
import model.Purchase;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public List<Purchase> getAllPurchases() {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
        return dao.getAll();
    }

    public ArrayList<Purchase> searchByDate(String date) {
        ArrayList<Purchase> purch =  null;
        return purch;
    }

    public ArrayList<Purchase> getPurchasesByClientId(int id) {
        ArrayList<Purchase> purch =  null;
        return purch;
    }

    public void deletePurchase(Purchase purchase) {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
        dao.delete(purchase);
     }

    public void addPurchase(int customerId, int itemId, LocalDate date) {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
        DAOCustomers daoCustomers = new DaoFactoryCustomers().getDaoCustomers();
        DAOItems daoItems = new DaoFactoryItems().getDaoItems();

        Purchase newPurchase = new Purchase();
        newPurchase.setId(-1);
        newPurchase.setDate(date);
        newPurchase.setCustomer(daoCustomers.get(customerId));
        newPurchase.setItem(daoItems.get(itemId));

        dao.save(newPurchase);
    }

    public void deletePurchasesByItemID(int idItem) {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
            dao.deleteByItemId(idItem);
    }

    public List<Purchase> findByDate(LocalDate selectedDate) {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
            return dao.getByDate(selectedDate);
    }

    public void deletePurchaseByCustomerId(int idCustomer) {
        DAOPurchases dao = new DaoFactoryPurchases().getDaoPurchases();
            dao.deleteByCustomerId(idCustomer);
    }
}
