package dao.dao_implementations.hibernate;

import dao.interfaces.DAOItems;
import dao.utils.DaoConstants;
import dao.utils.HibernateSessionFactory;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

@Log4j2
public class DaoHibernateItems implements DAOItems {
    Session session;
    @Override
    public Either<String, Item> get(int id) {
        session = HibernateSessionFactory.getSession();
        Either<String, Item> result;
        try {
            result = Either.right(
                    session
                            .createNamedQuery("get_item_by_id", Item.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
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
    public Either<String, List<Item>> getAll() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Item>> result;
        try{
            result = Either.right(
                    session.createNamedQuery("get_all_items", Item.class)
                            .getResultList()
            );
        }catch (HibernateException hibernateException){
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Item> save(Item t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Item> result;
        try{
            transaction = session.beginTransaction();
            int generatedKey = (int)session.save(t);
            t.setIdItem(generatedKey);
            transaction.commit();
            result = Either.right(t);
        }catch (HibernateException hibernateException){
            if (transaction != null){
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
    public Either<String, Item> update(Item t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Item> result;
        try{
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            result = Either.right(t);
        }catch (HibernateException hibernateException){
            if (transaction != null){
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
    public Either<String, Void> deleteWithoutPurchases(Item t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Void> result;
        try{
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            result = Either.right(null);
        }catch (HibernateException hibernateException){
            if (transaction != null){
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
    public Either<String, Void> deleteWithPurchases(Item t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Void> result;
        try{
            transaction = session.beginTransaction();
            t.getPurchasesByIdItem()
                    .forEach(purchase -> session.delete(purchase));
            session.delete(t);
            transaction.commit();
            result = Either.right(null);
        }catch (HibernateException hibernateException){
            if (transaction != null){
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }
}
