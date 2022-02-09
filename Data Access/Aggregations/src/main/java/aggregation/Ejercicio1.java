package aggregation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import mongo.MongoModule;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static java.util.Arrays.asList;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;



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
