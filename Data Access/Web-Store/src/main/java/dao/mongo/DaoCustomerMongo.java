package dao.mongo;

import dao.interfaces.DAOCustomers;
import io.vavr.control.Either;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Review;

import java.time.LocalDate;
import java.util.List;

public class DaoCustomerMongo implements DAOCustomers {

    @Override
    public Either<String, Customer> getCustomerById(String id) {
        return null;
    }

    @Override
    public Either<String, List<Customer>> getAllCustomers() {
        return null;
    }

    @Override
    public Either<String, Customer> saveCustomer(Customer t) {
        return null;
    }

    @Override
    public Either<String, Customer> updateCustomer(Customer t) {
        return null;
    }

    @Override
    public Either<String, Void> deleteCustomer(Customer t) {
        return null;
    }

    @Override
    public Either<String, Purchase> addPurchase(Purchase purchase, Customer customerToAdd) {
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
    public Either<String, List<Purchase>> getAllPurchases() {
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getPurchaseByClient(String clientId) {
        return null;
    }

    @Override
    public Either<String, Purchase> savePurchase(Purchase toAdd) {
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getPurchaseByDate(LocalDate selectedDate) {
        return null;
    }

    @Override
    public Either<String, Purchase> updatePurchase(Purchase updatedPurchase) {
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getSortedByItem() {
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getSortedByCustomer() {
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate) {
        return null;
    }
}
