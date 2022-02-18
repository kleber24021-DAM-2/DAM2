package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoUsuarios;
import org.quevedo.models.hibernate.EntityUsuarios;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Log4j2
public class DaoHibernateUsuarios implements DaoUsuarios {
    @Override
    public Either<String, EntityUsuarios> getUsuarioById(int getUsuarioId) {
        Either<String, EntityUsuarios> result;
        Session session = HibernateUtils.getSession();
        try {
            result = Either.right(session.get(EntityUsuarios.class, getUsuarioId));
        }catch (Exception e){
            result = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> removeUserById(int removeUsuarioId){
        Either<String, Void> result;
        Session session = HibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(getUsuarioById(removeUsuarioId).get());
            transaction.commit();
            result = Either.right(null);
        }catch (PersistenceException constraintViolationException){
            if (transaction != null){
                transaction.rollback();
            }
            result = Either.left("This user can't be deleted. It has associated permissions");
        }catch (Exception exception){
            if (transaction != null){
                transaction.rollback();
            }
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }finally {
            session.close();
        }
        return result;
    }

}
