package videogames;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BsonField;
import mongo.MongoModule;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.regex;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

/*
*Find all the publishers that have published multiplayer and multiplatform games and sort them by sales amount
 */
//Solution
/*
db.Videogames.aggregate([{
    $match: {
        $and: [
            {
                'Features.Max Players': {
                    $gt: 1
                }
            },
            {
                'Features.Multiplatform?': true
            }
        ]
    }
}, {
    $group: {
        _id: '$Metadata.Publishers',
        Sales: {
            $sum: '$Metrics.Sales'
        }
    }
}, {
    $sort: {
        Sales: -1
    }
}])
 */
public class Ejercicio4 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Videogames");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(and(gt("Features.Max Players", 1), new Document("Features.Multiplatfrom?", true))),
                group("$Metadata.Publishers", new BsonField("Sales", new Document("$sum", "$Metrics.Sales"))),
                sort(new Document("Sales", -1))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
