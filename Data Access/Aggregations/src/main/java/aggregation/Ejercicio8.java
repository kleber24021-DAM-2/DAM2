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
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/*
db.madrid.aggregate([
    {
        $group: {
            _id: "$event-location",
            count: {$sum: 1}
        }
    },
    {
        $group:{
            _id: null,
            AverageEvents:{$avg:"$count"}
        }
    }
])
 */


public class Ejercicio8 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");

        List<Bson> aggregatePipeline = Arrays.asList(
                group("$event-location", new BsonField("count", new Document("$sum", 1))),
                group(null, new BsonField("AverageEvents", new Document("$avg", "$count")))
        );

        System.out.println(collection.aggregate(aggregatePipeline).into(new ArrayList<>()).stream().findFirst());
    }
}
