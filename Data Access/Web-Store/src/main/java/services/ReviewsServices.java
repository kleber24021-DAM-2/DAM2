/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.dao_implementations.*;
import dao.interfaces.*;
import model.Review;

import java.util.List;

/**
 * @author Laura
 */
public class ReviewsServices {
    DAOItems daoItems = new DaoItemsHibernate();
    DAOCustomers daoCustomers = new DaoCustomersHibernate();
    DAOReviews daoReviews = new DaoReviewsHibernate();
    DAOPurchases daoPurchases = new DaoPurchaseHibernate();
    DAOUsers daoUsers = new DaoUserHibernate();

    public List<Review> getAllReviews() {
        return daoReviews.getAll();
    }

    public List<Review> getReviewsByCustomer(int customerId){
        return daoReviews.getByCustomerId(customerId);
    }

    public void deleteReview(Review review) {
        daoReviews.delete(review);
    }

    public List<Review> searchByItem(int id) {
        return daoReviews.getByItemId(id);
    }

    public Review addReview(Review review) {
        return daoReviews.save(review);
    }

    public void updateReview(Review review) {
        daoReviews.update(review);
    }


}
