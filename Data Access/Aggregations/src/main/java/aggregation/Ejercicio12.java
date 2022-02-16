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
db.Items.aggregate([{$unwind: {
        path: '$reviews'
    }}, {$group: {
        _id: '$_id',
        averageRating: {
            $avg: '$reviews.rate'
        }
    }}, {$match: {
        averageRating: {
            $gte: 4
        }
    }}, {$group: {
        _id: null,
        averageReviewGreaterThan4: {
            $sum: 1
        },
        {$project:{
            _id:0
            }
        }
    }}])
 */
public class Ejercicio12 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Item");

        List<Bson> aggregatePipeline = Arrays.asList(
                unwind("$reviews"),
                group("$_id", new BsonField("averageRating", new Document("$avg", "$reviews.rate"))),
                match(gte("averageRating", 4)),
                group(null, new BsonField("averageReviewGreaterThan4", new Document("$sum", 1))),
                project(new Document("_id", 0))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
