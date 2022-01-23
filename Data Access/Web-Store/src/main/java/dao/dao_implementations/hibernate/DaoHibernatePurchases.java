package dao.dao_implementations.hibernate;

import dao.interfaces.DAOPurchases;
import dao.utils.DaoConstants;
import dao.utils.HibernateSessionFactory;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Purchase;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class DaoHibernatePurchases implements DAOPurchases {
    private Session session;

    @Override
    public Either<String, Purchase> get(int id) {
        session = HibernateSessionFactory.getSession();
        Either<String, Purchase> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchase_by_id", Purchase.class)
                    .setParameter(DaoConstants.ID, id)
                    .getSingleResult());
        } catch (NoResultException noResult) {
            result = Either.left(DaoConstants.NO_RESULT_ERROR);
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getAll() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_all_purchase", Purchase.class)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Purchase> save(Purchase t) {
        session = HibernateSessionFactory.getSession();
        Either<String, Purchase> result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            int generatedKey = (int) session.save(t);
            t.setIdPurchase(generatedKey);
            transaction.commit();
            result = Either.right(t);
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Purchase> update(Purchase t) {
        session = HibernateSessionFactory.getSession();
        Either<String, Purchase> result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            result = Either.right(t);
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> delete(Purchase t) {
        session = HibernateSessionFactory.getSession();
        Either<String, Void> result;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            result = Either.right(null);
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getByCustomerId(int idCustomer) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchase_by_customer", Purchase.class)
                    .setParameter("idCustomer", idCustomer)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getByItemId(int idItem) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchase_by_item", Purchase.class)
                    .setParameter("idItem", idItem)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getByDate(LocalDate selectedDate) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchase_by_date", Purchase.class)
                    .setParameter("date", Date.valueOf(selectedDate))
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getSortedByItem() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchases_order_by_item", Purchase.class)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getSortedByCustomer() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchases_order_by_customer", Purchase.class)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate) {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Purchase>> result;
        try {
            result = Either.right(session.createNamedQuery("get_purchases_in_date_range", Purchase.class)
                    .setParameter("initialDate", initialDate)
                    .setParameter("finalDate", finalDate)
                    .getResultList());
        } catch (HibernateException hibernateException) {
            log.error(hibernateException.getMessage(), hibernateException);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }
}
