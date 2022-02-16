package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;


import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
db.madrid.aggregate([
    {
        $match: {
            "event-location":{$regex:"/*Latina"}
        }
    },
    {
        $group: {
            _id: "$event-location",
            count: {$sum: 1}
        }
    }
])
 */
public class Ejercicio6 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(regex("event-location", "/*Latina")),
                group("$event-location", new BsonField("count", new Document("$sum", 1)))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>()).forEach(System.out::println);
    }
}
