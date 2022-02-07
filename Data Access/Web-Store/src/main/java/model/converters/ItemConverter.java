package model.converters;

import model.customer.Purchase;
import model.item.Item;
import model.item.Review;
import org.bson.Document;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemConverter {
    PurchaseConverter purchaseConverter = new PurchaseConverter();
    ReviewConverter reviewConverter = new ReviewConverter();
    public Item documentToItem(Document d) {
        List<Document> purchasesDocument = d.getList("purchases", Document.class);
        List<Purchase> purchases = Collections.emptyList();
        if (purchasesDocument != null){
            purchases = purchasesDocument.stream().map(document -> purchaseConverter.toPurchaseFromDocument(document)).collect(Collectors.toList());
        }
        List<Document> reviewsDocument = d.getList("reviews", Document.class);
        List<Review> reviews = Collections.emptyList();
        if (reviewsDocument != null){
            reviews = reviewsDocument.stream().map(document -> reviewConverter.documentToReview(document)).collect(Collectors.toList());
        }
        return Item.builder()
                .id(d.getObjectId("_id"))
                .name(d.getString("name"))
                .company(d.getString("company"))
                .price(d.getDouble("price"))
                .purchases(purchases)
                .reviews(reviews)
                .build();
    }

    public Document itemToDocument(Item item) {
        Document d = new Document()
                .append("name", item.getName())
                .append("company", item.getCompany())
                .append("price", item.getPrice());
        if (!item.getPurchases().isEmpty()) {
            d.append("purchases", item.getPurchases());
        }
        if (!item.getReviews().isEmpty()){
            d.append("reviews", item.getReviews());
        }
        return d;
    }
}
