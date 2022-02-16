package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.gte;

/*
db.Customer.aggregate([{
    $lookup: {
        from: 'User',
        localField: '_id',
        foreignField: '_id',
        as: 'User'
    }
}, {
    $match: {
        User: []
    }
}])
 */

public class Ejercicio14 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Customer");

        List<Bson> aggregatePipeline = Arrays.asList(
                lookup("User", "_id", "_id", "User"),
                match(new Document("User", Collections.emptyList()))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
