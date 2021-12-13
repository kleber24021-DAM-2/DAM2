/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.dao_implementations.*;
import dao.interfaces.*;
import model.hibernatemodels.EntityCustomers;

import java.util.List;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    DAOItems daoItems = new DaoItemsHibernate();
    DAOCustomers daoCustomers = new DaoCustomersHibernate();
    DAOReviews daoReviews = new DaoReviewsHibernate();
    DAOPurchases daoPurchases = new DaoPurchaseHibernate();
    DAOUsers daoUsers = new DaoUserHibernate();

    public List<EntityCustomers> getAllCustomers() {
        return daoCustomers.getAll();
    }

    public EntityCustomers searchById(int id) {
        return daoCustomers.get(id);
    }

    public boolean deleteCustomer(EntityCustomers customer) {
        boolean result = false;
        if (daoPurchases.getByCustomerId(customer.getIdCustomer()).isEmpty()){
            daoCustomers.delete(customer);
            result = true;
        }
        return result;
    }

    public EntityCustomers addCustomer(EntityCustomers toAdd) {
        return daoCustomers.save(toAdd);
    }

    public boolean updateCustomer(EntityCustomers updatedCustomer) {
         return daoCustomers.update(updatedCustomer);
    }
}
