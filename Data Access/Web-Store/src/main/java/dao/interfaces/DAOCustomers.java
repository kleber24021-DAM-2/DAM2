/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;


import io.vavr.control.Either;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Review;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public interface DAOCustomers {
     
    Either<String, Customer> getCustomerById(String id);

    Either<String, List<Customer>> getAllCustomers();

    Either<String, Customer> saveCustomer(Customer t);

    Either<String, Customer> updateCustomer(Customer t);
     
    Either<String, Void> deleteCustomer(Customer t);

    Either<String, Purchase> addPurchase(Purchase purchase, Customer referenciaCustomer);

    Either<String, Void> deletePurchase(Purchase purchase);

    Either<String, Review> addReview(Review review, Purchase referenciaPurchase);

    Either<String, Void> deleteReview(Review review);

    Either<String, List<Purchase>> getAllPurchases();

    Either<String, List<Purchase>> getPurchaseByClient(String clientId);

    Either<String, Purchase> savePurchase(Purchase toAdd);

    Either<String, List<Purchase>> getPurchaseByDate(LocalDate selectedDate);

    Either<String, Purchase> updatePurchase(Purchase updatedPurchase);

    Either<String, List<Purchase>> getSortedByItem();

    Either<String, List<Purchase>> getSortedByCustomer();

    Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate);
}
