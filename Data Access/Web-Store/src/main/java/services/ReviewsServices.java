/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.interfaces.DAOItems;
import dao.mongo.DaoItemsMongo;
import io.vavr.control.Either;
import model.item.Item;
import model.item.Review;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Laura
 */
public class ReviewsServices {


    public Either<String, List<Review>> getAllReviews() {
//        DAOItems daoReviews = new DaoItemsMongo();
//        return daoReviews.getAllReviews();
        return null;
    }

    public Either<String, List<Review>> getReviewsByCustomer(String customerId){
//        DAOItems daoReviews = new DaoItemsMongo();
//        return daoReviews.getReviewByCustomerId(customerId);
        return null;
    }

    public Either<String, Void> deleteReview(Review review) {
        DAOItems daoReviews = new DaoItemsMongo();
        return daoReviews.deleteReview(review);
    }

    public Either<String, Review> addReview(Review review, ObjectId item) {
        DAOItems daoReviews = new DaoItemsMongo();
        return daoReviews.addReview(review, item);
    }
//
//
//    public Either<String, List<Review>> getReviewsSortedByDateItem(Item item) {
//        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
//        return daoReviews.getSortedByDate(item);
//    }
//
//    public Either<String, List<Review>> getReviewsSortedByRatingItem(Item item){
//        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
//        return daoReviews.getSortedByRating(item);
//    }
//
//    public Either<String, List<Review>> getReviewsByItem(Item selectedItem) {
//        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
//        return daoReviews.getByItemId(selectedItem.getIdItem());
//    }
//
//    public Either<String, List<Review>> getReviewsByRating(Ratings selectedRating) {
//        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
//        return daoReviews.getByRating(selectedRating);
//    }
}
