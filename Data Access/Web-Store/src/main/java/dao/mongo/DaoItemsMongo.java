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
    public Either<String, Item> save(Item t) {
        return null;
    }

    @Override
    public Either<String, Item> update(Item t) {
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
}
