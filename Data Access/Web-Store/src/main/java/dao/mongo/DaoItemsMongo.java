package dao.mongo;

import dao.interfaces.DAOItems;
import io.vavr.control.Either;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Item;
import model.item.Review;

import java.util.List;

public class DaoItemsMongo implements DAOItems {
    @Override
    public Either<String, Item> get(int id) {
        return null;
    }

    @Override
    public Either<String, List<Item>> getAll() {
        return null;
    }

    @Override
    public Either<String, Item> saveItem(Item t) {
        return null;
    }

    @Override
    public Either<String, Item> updateItem(Item t) {
        return null;
    }

    @Override
    public Either<String, Void> deleteWithoutPurchases(Item t) {
        return null;
    }

    @Override
    public Either<String, Void> deleteWithPurchases(Item t) {
        return null;
    }

    @Override
    public Either<String, Purchase> addPurchase(Purchase purchase, Customer referenciaCustomer) {
        return null;
    }

    @Override
    public Either<String, Void> deletePurchase(Purchase purchase) {
        return null;
    }

    @Override
    public Either<String, Review> addReview(Review review, Purchase referenciaPurchase) {
        return null;
    }

    @Override
    public Either<String, Void> deleteReview(Review review) {
        return null;
    }

    @Override
    public Either<String, List<Review>> getAllReviews() {
        return null;
    }

    @Override
    public Either<String, List<Review>> getReviewByCustomerId(String customerId) {
        return null;
    }

    @Override
    public Either<String, List<Review>> getReviewByItemId(String id) {
        return null;
    }

    @Override
    public Either<String, Review> saveReview(Review review) {
        return null;
    }

    @Override
    public Either<String, Review> updateReview(Review review) {
        return null;
    }
}
