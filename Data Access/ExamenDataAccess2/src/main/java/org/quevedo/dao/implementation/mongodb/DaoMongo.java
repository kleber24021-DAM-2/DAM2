package org.quevedo.dao.implementation.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.quevedo.config.ConfigConst;

public class DaoMongo implements org.quevedo.dao.interfaces.DaoMongo {

    @Override
    public void addUbicacion(Document document) {
        MongoClient mongo = MongoClients.create(ConfigConst.URL_MONGO);
        MongoDatabase db = mongo.getDatabase(ConfigConst.MONGO_DB);
        MongoCollection<Document> est = db.getCollection("Locations");

        est.insertOne(document);
    }
}
