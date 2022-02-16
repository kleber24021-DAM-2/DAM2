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

/*
db.Customer.aggregate([{
    $lookup: {
        from: 'Item',
        localField: 'purchases.name_item',
        foreignField: 'name',
        as: 'Item'
    }
}, {
    $unwind: {
        path: '$Item'
    }
}, {
    $group: {
        _id: '$name',
        totalBudget: {
            $sum: '$Item.price'
        }
    }
}])
 */
public class Ejercicio15 {
    public static void main(String[] args) {
        MongoDatabase database = MongoModule.getMongoInstance();
        MongoCollection<Document> collection = database.getCollection("Customer");

        List<Bson> aggregatePipeline = Arrays.asList(
                lookup("Item", "purchases.name_item", "name", "Item"),
                unwind("$Item"),
                group("$name", new BsonField("totalBudget", new Document("$sum", "$Item.price")))
        );

        collection.aggregate(aggregatePipeline).into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
