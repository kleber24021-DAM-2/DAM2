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

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.regex;

//Find the average rating and sales of all Simulation games by console
//Solution:
/*
db.Videogames.aggregate([{
    $match: {
        'Metadata.Genres': {
            $regex: '/*Simulation'
        }
    }
}, {
    $group: {
        _id: '$Release.Console',
        AverageRating: {
            $avg: '$Metrics.Review Score'
        }
    }
}])
 */
public class Ejercicio2 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Videogames");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(regex("Metadata.Genres", "/*Simulation")),
                group("$Release.Console", new BsonField("AverageRating", new Document("$avg", "$Metrics.Review Score")))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
