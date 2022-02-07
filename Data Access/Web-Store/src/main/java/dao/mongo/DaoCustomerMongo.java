package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import dao.interfaces.DAOCustomers;
import dao.utils.MongoModule;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.converters.CustomerConverter;
import model.converters.PurchaseConverter;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Review;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;


@Log4j2
public class DaoCustomerMongo implements DAOCustomers {

    public static final String ERROR_DB = "There was a problem connecting to the database";
    MongoCollection<Document> customersCollection = MongoModule.getMongoInstance().getCollection("Customers");
    CustomerConverter customerConverter = new CustomerConverter();
    PurchaseConverter purchaseConverter = new PurchaseConverter();

    @Override
    public Either<String, Customer> getCustomerById(String id) {
        Either<String, Customer> result;
        try {
            result = Either.right(customersCollection
                    .find(eq("_id", id))
                    .into(new ArrayList<>())
                    .stream()
                    .map(document -> customerConverter.documentToCustomer(document))
                    .findFirst().get());
        } catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, List<Customer>> getAllCustomers() {
        Either<String, List<Customer>> result;
        try {
            result = Either.right(customersCollection
                    .find()
                    .into(new ArrayList<>())
                    .stream()
                    .map(document -> customerConverter.documentToCustomer(document))
                    .collect(Collectors.toList()));
        } catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, Customer> saveCustomer(Customer t) {
        Either<String, Customer> result;
        try {
            Document tDocument = customerConverter.customerToDocument(t);
            customersCollection.insertOne(tDocument);
            result = Either.right(customerConverter.documentToCustomer(tDocument));
        } catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, Void> deleteCustomer(Customer t) {
        Either<String, Void> result;
        try {
            customersCollection.deleteOne(eq("_id", t.getId()));
            result = Either.right(null);
        } catch (MongoException mongoException) {
            log.error(mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, Purchase> addPurchase(Purchase purchase, Customer customerToAdd) {
        Either<String, Purchase> result;
        try {
            Bson updates = Updates.combine(
                    Updates.addToSet("purchases", purchaseConverter.toCustomerPurchaseDocument(purchase))
            );
            customersCollection.updateOne(eq("_id", customerToAdd.getId()), updates);
            result = Either.right(purchase);
        } catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, Void> deletePurchase(Purchase purchase) {
        Either<String, Void> result;
        try {
            Document toRemove = new Document("purchases", new Document("id_purchase", purchase.getIdPurchase()));
            Document document = new Document("$pull", toRemove);
            customersCollection.updateOne(eq("_id", purchase.getIdItem()), document);
            result = Either.right(null);
        } catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, Review> addReview(Review review, Purchase referenciaPurchase) {
        Either<String, Review> result;
        // TODO: 07/02/2022
        return null;
    }

    @Override
    public Either<String, Void> deleteReview(Review review) {
        // TODO: 07/02/2022
        return null;
    }

    @Override
    public Either<String, List<Purchase>> getAllPurchases() {
        Either<String, List<Purchase>> result;
        try {
            List<Purchase> purchases = customersCollection
                    .aggregate(Collections.singletonList(new Document("$project",
                            new Document("_id", 0L)
                                    .append("purchases", 1L))))
                    .into(new ArrayList<>())
                    .stream()
                    .map(document -> document.getList("purchases", Document.class))
                    .flatMap(Collection::stream)
                    .map(document -> purchaseConverter.toPurchaseFromDocument(document))
                    .collect(Collectors.toList());
            result = Either.right(purchases);
        }catch (NullPointerException nullPointerException){
            result = Either.right(Collections.emptyList());
        }catch (MongoException mongoException) {
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getPurchaseByClient(String clientId) {
        return null;
    }

    @Override
    public Either<String, Purchase> savePurchase(Purchase toAdd, Customer customerToAdd) {
        Either<String, Purchase> result;
        try {
            Bson updates = Updates.combine(
                    Updates.addToSet("purchases", purchaseConverter.toCustomerPurchaseDocument(toAdd))
            );
            customersCollection.updateOne(eq("_id", customerToAdd.getId()), updates);
            result = Either.right(toAdd);
        }catch (MongoException mongoException){
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(ERROR_DB);
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getPurchaseByDate(LocalDate selectedDate) {
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
