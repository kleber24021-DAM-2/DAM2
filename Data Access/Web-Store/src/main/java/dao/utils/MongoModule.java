package dao.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Properties;

public class MongoModule {
    private static MongoClient mongoClient = null;
    private static MongoDatabase db = null;
    private static Properties properties = new Properties();

    private MongoModule() {
    }

    public static MongoDatabase getMongoInstance() {
        if (mongoClient == null) {
            String mongoPath = properties.getProperty("urlDB");
            mongoClient = MongoClients.create(mongoPath);
            db = mongoClient.getDatabase("lucia");
        }
        return db;
    }
}
