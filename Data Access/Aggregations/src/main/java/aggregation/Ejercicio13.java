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
import static com.mongodb.client.model.Filters.gte;

/*
db.Customer.aggregate([{
    $unwind: {
        path: '$purchases'
    }
}, {
    $unwind: {
        path: '$purchases.review'
    }
}, {
    $match: {
        'purchases.review.rate': {
            $gte: 3
        }
    }
}, {
    $group: {
        _id: '$name'
    }
}])
 */

public class Ejercicio13 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Customer");

        List<Bson> aggregatePipeline = Arrays.asList(
                unwind("$purchases"),
                unwind("$purchases.review"),
                match(gte("purchases.review.rate", 3)),
                group("$name")
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
