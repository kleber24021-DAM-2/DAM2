/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import io.vavr.control.Either;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Item;
import model.item.Review;

import java.util.List;

/**
 *
 */
public interface DAOItems {

    Either<String, Item> get(int id);
     
    Either<String, List<Item>> getAll();

    Either<String, Item> save(Item t);

    Either<String, Item> update(Item t);
     
    Either<String, Void> deleteWithoutPurchases(Item t);

    Either<String, Void> deleteWithPurchases(Item t);

    Either<String, Purchase> addPurchase(Purchase purchase, Customer referenciaCustomer);

    Either<String, Void> deletePurchase(Purchase purchase);

    Either<String, Review> addReview(Review review, Purchase referenciaPurchase);

    Either<String, Void> deleteReview(Review review);
}
