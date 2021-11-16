/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.daofactories.DaoFactoryReviews;
import dao.interfaces.DAOReviews;
import model.Review;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Laura
 */
public class ReviewsServices {


    public List<Review> getAllReviews() {
        DAOReviews daoReviews = new DaoFactoryReviews().getDaoReviews();
        return daoReviews.getAll();
    }

    public void deleteReview(Review review) {
        DAOReviews daoReviews = new DaoFactoryReviews().getDaoReviews();
        daoReviews.delete(review);
    }

    public List<Review> searchByItem(int id) {
        DAOReviews daoReviews = new DaoFactoryReviews().getDaoReviews();
        return daoReviews.getAll().stream()
                .filter(r -> r.getPurchase().getItem().getIdItem() == id)
                .collect(Collectors.toList());
    }

    public void addReview(Review review) {
        DAOReviews daoReviews = new DaoFactoryReviews().getDaoReviews();
        daoReviews.save(review);
    }

    public void updateReview(Review review) {
        DAOReviews daoReviews = new DaoFactoryReviews().getDaoReviews();
        daoReviews.update(review);
    }
}
