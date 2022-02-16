package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;

/*
db.Customer.aggregate([{$unwind: {
        path: '$purchases'
    }}, {$unwind: {
        path: '$purchases.review'
    }}, {$group: {
        _id: '$_id',
        reviews: {
            $sum: 1
        }
    }}, {$sort: {
        reviews: -1
    }}, {$limit: 1}])
 */
public class Ejercicio11 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Customer");

        List<Bson> aggregatePipeline = Arrays.asList(
                unwind("$purchases"),
                unwind("$purchases.review"),
                group("$_id", new BsonField("reviews", new Document("$sum", 1))),
                sort(new Document("reviews", -1)),
                limit(1)
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
