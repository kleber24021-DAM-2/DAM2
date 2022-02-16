package aggregation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongo.MongoModule;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Ejercicio3 {
    /*
    [{$match: {
 $expr: {
  $eq: [
   {
    $month: {
     $toDate: '$dtstart'
    }
   },
   1
  ]
 }
}}, {$project: {
 title: 1,
 _id: 0
}}]
     */

    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");
        collection.aggregate(Arrays.asList(new Document("$match",
                        new Document("$expr",
                                new Document("$eq", Arrays.asList(new Document("$month",
                                        new Document("$toDate", "$dtstart")), 1L)))),
                new Document("$project",
                        new Document("title", 1L)
                                .append("_id", 0L)))).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
