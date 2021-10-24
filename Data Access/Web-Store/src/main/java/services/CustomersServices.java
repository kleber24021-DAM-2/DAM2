/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

import dao.dao_implementations.NioDAOPurchases;
import dao.dao_implementations.XMLDaoCustomers;
import model.Customer;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public List<Customer> getAllCustomers() {
        XMLDaoCustomers dao = new XMLDaoCustomers();
        return dao.getAll();
    }

    public Customer searchById(int id) {
        XMLDaoCustomers dao = new XMLDaoCustomers();
        return dao.get(id);
    }

    public boolean deleteCustomer(Customer customer) {
        XMLDaoCustomers daoCustomers = new XMLDaoCustomers();
        NioDAOPurchases daoPurchases = new NioDAOPurchases();
        if (!daoPurchases.getByCustomerId(customer.getIdCustomer()).isEmpty()){
            return false;
        }
        daoCustomers.delete(customer);
        return true;
    }

    public boolean addCustomer(int customerId, String name, String phone, String address) {
        Customer toAdd = new Customer(customerId, name, phone, address);
        XMLDaoCustomers dao = new XMLDaoCustomers();
        return dao.save(toAdd);
    }

}
