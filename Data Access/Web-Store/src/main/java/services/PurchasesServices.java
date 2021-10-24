/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.dao_implementations.NioDAOPurchases;
import model.Purchase;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public List<Purchase> getAllPurchases() {
        NioDAOPurchases dao = new NioDAOPurchases();
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
        NioDAOPurchases dao = new NioDAOPurchases();
        dao.delete(purchase);
     }

    public void addPurchase(int customerId, int itemId, LocalDate date) {
        NioDAOPurchases dao = new NioDAOPurchases();
        Purchase newPurchase = new Purchase();
        newPurchase.setIdPurchase(dao.getLastId()+1);
        newPurchase.setDate(date);
        newPurchase.setIdCustomer(customerId);
        newPurchase.setIdItem(itemId);

        dao.save(newPurchase);
    }

    public void deletePurchasesByItemID(int idItem) {
        NioDAOPurchases dao = new NioDAOPurchases();
        dao.deleteByItemId(idItem);
    }

    public List<Purchase> findByDate(LocalDate selectedDate) {
        NioDAOPurchases dao = new NioDAOPurchases();
        return dao.getByDate(selectedDate);
    }

    public void deletePurchaseByCustomerId(int idCustomer) {
        NioDAOPurchases dao = new NioDAOPurchases();
        dao.deleteByCustomerId(idCustomer);
    }
}
