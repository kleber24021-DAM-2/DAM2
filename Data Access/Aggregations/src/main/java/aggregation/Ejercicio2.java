package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class Ejercicio2 {
    /*
    [{
    $match: {
        'event-location': {
            $regex: '/*Latina'
        }
    }
    }]
     */

    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");
        List<Bson> bsonList = Arrays.asList(
                match(regex("event-location", "/*Latina"))
        );
        collection.aggregate(bsonList).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
