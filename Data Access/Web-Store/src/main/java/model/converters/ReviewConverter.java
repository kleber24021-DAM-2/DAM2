package model.converters;

import model.item.Review;
import org.bson.Document;


public class ReviewConverter {
    public Review documentToReview(Document document){
        if (document == null){
            return null;
        }
        return Review.builder()
                .idReview(document.getString("id_review"))
                .title(document.getString("title"))
                .description(document.getString("description"))
                .rating(document.getInteger("rating"))
                .date(document.getString("date"))
                .build();
    }

    public Document reviewToDocument(Review review){
        return new Document()
                .append("id_review", review.getIdReview())
                .append("title", review.getTitle())
                .append("description", review.getDescription())
                .append("rating", review.getRating())
                .append("date", review.getDate());
    }
}
