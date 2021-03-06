package aggregation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/*[{
    $match: {
        'event-location': 'Auditorio y sala de exposiciones Paco de Lucía (Latina)'
    }
}, {
    $project: {
        _id: 0,
        '@type': 1,
        title: 1
    }
}] */

public class Ejercicio1 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("madrid");

        List<Bson> bsonList = Arrays.asList(
                match(eq("event-location", "Auditorio y sala de exposiciones Paco de Lucía (Latina)")),
                project(fields(include("@type", "title")))
        );
        collection.aggregate(bsonList).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
