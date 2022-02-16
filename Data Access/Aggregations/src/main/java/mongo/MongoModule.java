package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoModule {
    private static MongoClient mongoClient = null;
    private static MongoDatabase db = null;

    private MongoModule() {
    }

    public static MongoDatabase getMongoInstance() {
        if (mongoClient == null) {
//            String mongoPath = "mongodb://10.0.0.1:27017";
            String mongoPath = "mongodb://informatica.iesquevedo.es:2323";
            mongoClient = MongoClients.create(mongoPath);
            db = mongoClient.getDatabase("andre");
        }
        return db;
    }
}
