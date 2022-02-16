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
import static com.mongodb.client.model.Filters.*;

//Find the cheapest game with that has a duration of at least 20 hours in Main Story Leisure playstyle and can be played at PSP
//Solucion
/*
db.Videogames.aggregate([{
    $match: {
        $and: [
            {
                'Release.Console': {
                    $regex: '/*PSP'
                }
            },
            {
                'Length.Main Story.Leisure': {
                    $gte: 20
                }
            }
        ]
    }
}, {
    $sort: {
        'Metrics.Used Price': 1
    }
}, {$limit: 1}])
 */
public class Ejercicio3 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Videogames");

        List<Bson> aggregatePipeline = Arrays.asList(
                match(and(regex("Release.Console", "/*PSP"), gte("Length.Main Story.Leisure", 20))),
                sort(new Document("Metrics.Used Price", 1)),
                limit(1)
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
