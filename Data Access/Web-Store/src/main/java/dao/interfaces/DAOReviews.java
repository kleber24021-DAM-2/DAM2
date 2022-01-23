/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import gui.controllers.reviews.Ratings;
import io.vavr.control.Either;
import model.Item;
import model.Review;

import java.util.List;

/**
 *
 */
public interface DAOReviews {

    Either<String, Review> get(int id);

    Either<String, List<Review>> getAll();

    Either<String, List<Review>> getByCustomerId(int customerId);

    Either<String, List<Review>> getByItemId(int itemId);

    Either<String, List<Review>> getByPurchaseId(int purchaseId);

    Either<String, Review> save(Review t);

    Either<String, Review> update(Review t);

    Either<String, Void> delete(Review t);

    Either<String, List<Review>> getSortedByDate(Item item);

    Either<String, List<Review>> getSortedByRating(Item item);

    Either<String, List<Review>> getByRating(Ratings selectedRating);
}
