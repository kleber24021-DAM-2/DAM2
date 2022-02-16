package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;

/*
db.madrid.aggregate([
    {
        $match: {
            $expr: {
                $eq: [
                    {
                        $month: {
                            $toDate: "$dtstart"
                        }
                    },
                    1
                ]
            }
        }
    }
])
 */

public class Ejercicio4 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");

        List<Bson> documentList = Arrays.asList(
                new Document("$match",
                        new Document("$expr",
                                new Document("$eq", Arrays.asList(new Document("$month",
                                        new Document("$toDate", "$dtstart")), 1L)))),
                match(regex("event-location", "/*Latina")),
                count("Total")
        );
        collection.aggregate(documentList).into(new ArrayList<>()).forEach(System.out::println);
    }
}
