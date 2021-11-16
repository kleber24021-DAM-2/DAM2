/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOReviews;
import model.Review;

import java.util.List;

/**
 * @author Laura
 */
public class ReviewsServices {


    public List<Review> getAllReviews() {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getAll();
    }

    public List<Review> getReviewsByCustomer(int customerId){
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByCustomerId(customerId);
    }

    public void deleteReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        daoReviews.delete(review);
    }

    public List<Review> searchByItem(int id) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.getByItemId(id);
    }

    public Review addReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        return daoReviews.save(review);
    }

    public void updateReview(Review review) {
        DAOReviews daoReviews = DaoFactory.getInstance().getDaoReviews();
        daoReviews.update(review);
    }


}
