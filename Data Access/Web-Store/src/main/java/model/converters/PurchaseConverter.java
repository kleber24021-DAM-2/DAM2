package model.converters;

import model.customer.Purchase;
import model.item.Review;
import org.bson.Document;

public class PurchaseConverter {
    public Document toItemPurchaseDocument(Purchase purchase){
        return new Document()
                .append("id_purchase", purchase.getIdPurchase())
                .append("date", purchase.getDate());
    }

    public Document toCustomerPurchaseDocument(Purchase purchase){
        Document d = new Document()
                .append("id_purchase", purchase.getIdPurchase())
                .append("date", purchase.getDate())
                .append("id_item", purchase.getIdItem());
        if (purchase.getReview() != null){
            d.append("review", purchase.getReview());
        }
        return d;
    }

    public Purchase toPurchaseFromDocument(Document document){
        ReviewConverter reviewConverter = new ReviewConverter();

        Document reviewDocument = document.get("review", Document.class);
        Review review = null;
        if (reviewDocument != null){
            review = reviewConverter.documentToReview(reviewDocument);
        }

        Purchase purchase = Purchase.builder()
                .idPurchase(document.getString("id_purchase"))
                .date(document.getString("date"))
                .idItem(document.getString("id_item"))
                .build();

        if (review != null){
            purchase.setReview(review);
        }

        return purchase;
    }


}
