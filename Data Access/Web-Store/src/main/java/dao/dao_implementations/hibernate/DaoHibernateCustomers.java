package dao.dao_implementations.hibernate;

import dao.interfaces.DAOCustomers;
import dao.utils.DaoConstants;
import dao.utils.HibernateSessionFactory;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

@Log4j2
public class DaoHibernateCustomers implements DAOCustomers {
    private Session session;

    @Override
    public Either<String, Customer> get(int id) {
        session = HibernateSessionFactory.getSession();
        Either<String, Customer> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_customer_by_id", Customer.class)
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
    public Either<String, List<Customer>> getAll() {
        session = HibernateSessionFactory.getSession();
        Either<String, List<Customer>> result;
        try {
            result = Either.right(session
                    .createNamedQuery("get_all_customers", Customer.class)
                    .getResultList());
        } catch (HibernateException hibExcep) {
            log.error(hibExcep.getMessage(), hibExcep);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Customer> save(Customer t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Customer> result;
        User associatedUser = new User();
        associatedUser.setUsername(t.getName());
        associatedUser.setPassword(t.getName());
        try {
            transaction = session.beginTransaction();
            int newPk = (Integer) session.save(associatedUser);
            t.setIdCustomer(newPk);
            session.save(t);
            transaction.commit();
            result = Either.right(t);
        } catch (HibernateException hibExcep) {
            if (transaction != null){
                transaction.rollback();
            }
            log.error(hibExcep.getMessage(), hibExcep);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Customer> update(Customer t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Customer> result;
        try{
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            result = Either.right(t);
        }catch (HibernateException hibExcep){
            if (transaction != null){
                transaction.rollback();
            }
            log.error(hibExcep.getMessage(), hibExcep);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> delete(Customer t) {
        session = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        Either<String, Void> result;
        try{
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            result = Either.right(null);
        }catch (HibernateException hibExcep){
            if (transaction != null){
                transaction.rollback();
            }
            log.error(hibExcep.getMessage(), hibExcep);
            result = Either.left(DaoConstants.HIBERNATE_ERROR);
        }finally {
            session.close();
        }
        return result;
    }
}
