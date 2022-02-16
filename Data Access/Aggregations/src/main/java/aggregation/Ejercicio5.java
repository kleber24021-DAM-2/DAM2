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

import static com.mongodb.client.model.Aggregates.group;


/*db.madrid.aggregate([
    {
        $group: {
            _id: "$event-location",
            count: {$sum: 1}
        }
    }
])*/

public class Ejercicio5 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");
        BsonField accumulator = new BsonField("count", new Document("$sum", 1));
        List<Bson> documentList = Arrays.asList(
                group("$event-location", accumulator)
        );
        collection.aggregate(documentList).into(new ArrayList<>()).forEach(System.out::println);
    }
}
