package dao.dao_implementations.hibernate;

import dao.interfaces.DAOReviews;
import dao.utils.DaoConstants;
import dao.utils.HibernateSessionFactory;
import gui.controllers.reviews.Ratings;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Item;
import model.Review;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

@Log4j2
public class DaoHibernateReviews implements DAOReviews {
    private Session session;
    @Override
    public Either<String, Review> get(int id) {
        session = HibernateSessionFactory.getSession();
        Either<String, Review> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_review_by_id", Review.class)
                    .setParameter(DaoConstants.ID, id)
                    .getSingleResult());
        }catch (NoResultException noResult){
            result = Either.left(DaoConstants.NO_RESULT_ERROR);
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getAll() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_all_reviews", Review.class)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getByCustomerId(int customerId) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_customer", Review.class)
                    .setParameter("customerId", customerId)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getByItemId(int itemId) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_item", Review.class)
                    .setParameter("itemId", itemId)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getByPurchaseId(int purchaseId) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_purchase", Review.class)
                    .setParameter("purchaseId", purchaseId)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Review> save(Review t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Review> result;
        try {
            transaction = session.beginTransaction();
            int generatedKey = (int) session.save(t);
            t.setIdReview(generatedKey);
            transaction.commit();
            result = Either.right(t);
        }catch (HibernateException hibernateException){
            if(transaction != null){
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Review> update(Review t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Review> result;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            result = Either.right(t);
        }catch (HibernateException hibernateException){
            if(transaction != null){
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> delete(Review t) {
        session = HibernateSessionFactory.getSession();
        Either<String, Void> result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            result = Either.right(null);
        }catch (HibernateException hibernateException){
            if(transaction != null){
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getSortedByDate(Item item) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_item_sorted_by_date", Review.class)
                    .setParameter("item", item)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getSortedByRating(Item item) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_item_sorted_by_rating", Review.class)
                    .setParameter("item", item)
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Review>> getByRating(Ratings selectedRating) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Review>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_reviews_by_rating", Review.class)
                    .setParameter("rating", selectedRating.getValue())
                    .getResultList());
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }
}
