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
     
    Either<String, List<Item>> getAll();

    Either<String, Item> saveItem(Item t);
     
    Either<String, Void> deleteItem(Item t);

    Either<String, Purchase> addPurchase(Purchase purchase, Customer toAdd);

    Either<String, Void> deletePurchase(Purchase purchase);

    Either<String, Review> addReview(Review review, Item toAdd);

    Either<String, Void> deleteReview(Review review);
}
