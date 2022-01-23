/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;


import io.vavr.control.Either;
import model.Customer;

import java.util.List;

/**
 *
 */
public interface DAOCustomers {
     
    Either<String, Customer> get(int id);

    Either<String, List<Customer>> getAll();

    Either<String, Customer> save(Customer t);

    Either<String, Customer> update(Customer t);
     
    Either<String, Void> delete(Customer t);
}
