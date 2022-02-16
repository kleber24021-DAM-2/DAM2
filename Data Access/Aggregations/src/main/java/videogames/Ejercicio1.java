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

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.regex;

//Find what year was the worst for Ubisoft in terms of sales
//Solution
/*
use andre
db.Videogames.aggregate([{
    $match: {
        'Metadata.Publishers': {
            $regex: '/*Ubisoft'
        }
    }
}, {
    $group: {
        _id: '$Release.Year',
        AverageSales: {
            $sum: '$Metrics.Sales'
        }
    }
}, {
    $sort: {
        AverageSales: 1
    }
}, {$limit: 1}])
 */
public class Ejercicio1 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Videogames");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(regex("Metadata.Publishers", "/*Ubisoft")),
                group("$Release.Year", new BsonField("AverageSales", new Document("$sum", "$Metrics.Sales"))),
                sort(new Document("AverageSales", 1)),
                limit(1)
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
