/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOCustomers;
import dao.mongo.DaoCustomerMongo;
import io.vavr.control.Either;
import model.customer.Customer;

import java.util.List;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public Either<String, List<Customer>> getAllCustomers() {
        DAOCustomers dao =  new DaoCustomerMongo();
        return dao.getAllCustomers();
    }

    public Either<String, Customer> searchById(String id) {
        DAOCustomers dao = new DaoCustomerMongo();
        return dao.getCustomerById(id);
    }

    public Either<String, Void> deleteCustomer(Customer customer) {
        DAOCustomers daoCustomers = new DaoCustomerMongo();
        Either<String, Customer> customerCheck = daoCustomers.getCustomerById(customer.getId());
        Either<String, Void> deleteResult;
        if (customerCheck.isRight()){
            if (customerCheck.get().getPurchases().isEmpty()){
                daoCustomers.deleteCustomer(customer);
                deleteResult = Either.right(null);
            }else {
                deleteResult = Either.left("This customer can't be deleted. It has purchases associated");
            }
        }else {
            deleteResult = Either.left(customerCheck.getLeft());
        }
        return deleteResult;
    }

    public Either<String, Customer> addCustomer(Customer toAdd) {
        DAOCustomers dao = new DaoCustomerMongo();
        return dao.saveCustomer(toAdd);
    }

    public Either<String, Customer> updateCustomer(Customer updatedCustomer) {
        DAOCustomers daoCustomers = new DaoCustomerMongo();
         return daoCustomers.updateCustomer(updatedCustomer);
    }
}
