package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import dao.interfaces.DAOItems;
import dao.utils.MongoModule;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.converters.ItemConverter;
import model.converters.PurchaseConverter;
import model.converters.ReviewConverter;
import model.customer.Customer;
import model.customer.Purchase;
import model.item.Item;
import model.item.Review;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@Log4j2
public class DaoItemsMongo implements DAOItems {

    public static final String DB_ERROR = "There was an error connection to the database";
    MongoCollection<Document> itemCollection = MongoModule.getMongoInstance().getCollection("Items");
    ItemConverter itemConverter = new ItemConverter();
    PurchaseConverter purchaseConverter = new PurchaseConverter();
    ReviewConverter reviewConverter = new ReviewConverter();

    @Override
    public Either<String, List<Item>> getAll() {
        Either<String, List<Item>> result;
        try {
            result = Either.right(itemCollection
                    .find()
                    .into(new ArrayList<>())
                    .stream()
                    .map(document -> itemConverter.documentToItem(document))
                    .collect(Collectors.toList()));
        }catch (MongoException exception){
         log.error(exception.getMessage(), exception);
         result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Item> saveItem(Item t) {
        Either<String, Item> result;
        try {
            Document tDocument = itemConverter.itemToDocument(t);
            itemCollection.insertOne(tDocument);
            result = Either.right(itemConverter.documentToItem(tDocument));
        }catch (MongoException mongoEx){
            log.error(mongoEx.getMessage(), mongoEx);
            result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Void> deleteItem(Item t) {
        Either<String, Void> result;
        try {
            itemCollection.deleteOne(eq("_id", t.getId()));
            result = Either.right(null);
        }catch (MongoException mongoEx){
            log.error(mongoEx.getMessage(), mongoEx);
            result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Purchase> addPurchase(Purchase purchase, Customer toAdd) {
        Either<String, Purchase> result;
        try {
            Bson updates = Updates.combine(
                    Updates.addToSet("purchases", purchaseConverter.toItemPurchaseDocument(purchase))
            );
            itemCollection.updateOne(eq("_id", new ObjectId(purchase.getIdItem())), updates);
            result = Either.right(purchase);
        }catch (MongoException mongoException){
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Void> deletePurchase(Purchase purchase) {
        Either<String, Void> result;
        try {
            Document toRemove = new Document("purchases", new Document("id_purchase", purchase.getIdPurchase()));
            Document document = new Document("$pull", toRemove);
            itemCollection.updateOne(eq("_id", purchase.getIdItem()), document);
            result = Either.right(null);
        }catch (MongoException mongoException){
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Review> addReview(Review review, ObjectId toAdd) {
        Either<String, Review> result;
        try {
            Bson updates = Updates.combine(
                    Updates.addToSet("reviews", reviewConverter.reviewToDocument(review))
            );
            itemCollection.updateOne(eq("_id", toAdd), updates);
            result = Either.right(review);
        }catch (MongoException mongoException){
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(DB_ERROR);
        }
        return result;
    }

    @Override
    public Either<String, Void> deleteReview(Review review) {
        Either<String, Void> result;
        try {
            Document toRemove = new Document("reviews", new Document("id_review", review.getIdReview()));
            Document document = new Document("$pull", toRemove);
            itemCollection.updateOne(eq("reviews.id_review", review.getIdReview()), document);
            result = Either.right(null);
        }catch (MongoException mongoException){
            log.error(mongoException.getMessage(), mongoException);
            result = Either.left(DB_ERROR);
        }
        return result;
    }
}
