/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

import dao.daofactories.DaoFactoryCustomers;
import dao.daofactories.DaoFactoryPurchases;
import dao.interfaces.DAOCustomers;
import dao.interfaces.DAOPurchases;
import model.Customer;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public List<Customer> getAllCustomers() {
        DAOCustomers dao = new DaoFactoryCustomers().getDaoCustomers();
        return dao.getAll();
    }

    public Customer searchById(int id) {
        DAOCustomers dao = new DaoFactoryCustomers().getDaoCustomers();
        return dao.get(id);
    }

    public boolean deleteCustomer(Customer customer) {
        DAOCustomers daoCustomers = new DaoFactoryCustomers().getDaoCustomers();
        DAOPurchases daoPurchases = new DaoFactoryPurchases().getDaoPurchases();

        if (!daoPurchases.getByCustomerId(customer.getIdCustomer()).isEmpty()){
            return false;
        }
        daoCustomers.delete(customer);
        return true;
    }

    public boolean addCustomer(int customerId, String name, String phone, String address) {
        Customer toAdd = new Customer(customerId, name, phone, address);
        DAOCustomers dao = new DaoFactoryCustomers().getDaoCustomers();
        return dao.save(toAdd);
    }

    public void updateCustomer(Customer updatedCustomer) {
        DAOCustomers daoCustomers = new DaoFactoryCustomers().getDaoCustomers();
        daoCustomers.update(updatedCustomer);
    }
}
