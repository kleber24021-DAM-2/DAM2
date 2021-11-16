/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOCustomers;
import dao.interfaces.DAOPurchases;
import model.Customer;

import java.util.List;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public List<Customer> getAllCustomers() {
        DAOCustomers dao =  DaoFactory.getInstance().getDaoCustomers();
        return dao.getAll();
    }

    public Customer searchById(int id) {
        DAOCustomers dao = DaoFactory.getInstance().getDaoCustomers();
        return dao.get(id);
    }

    public boolean deleteCustomer(Customer customer) {
        DAOCustomers daoCustomers = DaoFactory.getInstance().getDaoCustomers();
        DAOPurchases daoPurchases = DaoFactory.getInstance().getDaoPurchases();
        boolean result = false;
        if (daoPurchases.getByCustomerId(customer.getIdCustomer()).isEmpty()){
            daoCustomers.delete(customer);
            result = true;
        }
        return result;
    }

    public Customer addCustomer(Customer toAdd) {
        DAOCustomers dao = DaoFactory.getInstance().getDaoCustomers();
        return dao.save(toAdd);
    }

    public void updateCustomer(Customer updatedCustomer) {
        DAOCustomers daoCustomers = DaoFactory.getInstance().getDaoCustomers();
        daoCustomers.update(updatedCustomer);
    }
}
