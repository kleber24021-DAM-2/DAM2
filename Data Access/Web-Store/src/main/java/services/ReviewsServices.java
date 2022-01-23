/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOReviews;
import gui.controllers.reviews.Ratings;
import io.vavr.control.Either;
import model.Item;
import model.Review;

import java.util.List;

/**
 * @author Laura
 */
public class ReviewsServices {


    public Either<String, List<Review>> getAllReviews() {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getAll();
    }

    public Either<String, List<Review>> getReviewsByCustomer(int customerId){
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByCustomerId(customerId);
    }

    public Either<String, Void> deleteReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.delete(review);
    }

    public Either<String, List<Review>> searchByItem(int id) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByItemId(id);
    }

    public Either<String, Review> addReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.save(review);
    }

    public Either<String, Review> updateReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.update(review);
    }


    public Either<String, List<Review>> getReviewsSortedByDateItem(Item item) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getSortedByDate(item);
    }

    public Either<String, List<Review>> getReviewsSortedByRatingItem(Item item){
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getSortedByRating(item);
    }

    public Either<String, List<Review>> getReviewsByItem(Item selectedItem) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByItemId(selectedItem.getIdItem());
    }

    public Either<String, List<Review>> getReviewsByRating(Ratings selectedRating) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByRating(selectedRating);
    }
}
