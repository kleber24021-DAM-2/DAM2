package videogames;

/*
* Find the average rating of all the games that have a length of more than 20 hours in a rushed playstyle
 */

//Solution
/*
[{
    $match: {
        'Length.All PlayStyles.Rushed': {
            $gte: 20
        }
    }
}, {
    $group: {
        _id: null,
        averageRating: {
            $avg: '$Metrics.Review Score'
        }
    }
}]
 */


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
import static com.mongodb.client.model.Filters.*;

public class Ejercicio5 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Videogames");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(gte("Length.All PlayStyles.Rushed", 20)),
                group(null, new BsonField("averageRating", new Document("$avg", "$Metrics.Review Score")))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
