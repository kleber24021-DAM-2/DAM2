/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOCustomers;
import io.vavr.control.Either;
import model.customer.Customer;

import java.util.List;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public Either<String, List<Customer>> getAllCustomers() {
        DAOCustomers dao =  DaoFactory.getInstance().getDaoCustomers();
        return dao.getAll();
    }

    public Either<String, Customer> searchById(int id) {
        DAOCustomers dao = DaoFactory.getInstance().getDaoCustomers();
        return dao.get(id);
    }

    public Either<String, Void> deleteCustomer(Customer customer) {
        DAOCustomers daoCustomers = DaoFactory.getInstance().getDaoCustomers();
        Either<String, Customer> customerCheck = daoCustomers.get(customer.getIdCustomer());
        Either<String, Void> deleteResult;
        if (customerCheck.isRight()){
            if (customerCheck.get().getPurchasesByIdCustomer().isEmpty()){
                daoCustomers.delete(customer);
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
        DAOCustomers dao = DaoFactory.getInstance().getDaoCustomers();
        return dao.save(toAdd);
    }

    public Either<String, Customer> updateCustomer(Customer updatedCustomer) {
        DAOCustomers daoCustomers = DaoFactory.getInstance().getDaoCustomers();
         return daoCustomers.update(updatedCustomer);
    }
}
