package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoPermisosObjetos;
import org.quevedo.models.hibernate.EntityPermisosObjetos;

@Log4j2
public class DaoHibernatePermisosObjetos implements DaoPermisosObjetos {

    @Override
    public Either<String, EntityPermisosObjetos> addPermissionToUser(EntityPermisosObjetos permiso) {
        Either<String, EntityPermisosObjetos> result;
        Transaction transaction = null;
        Session session = HibernateUtils.getSession();
        try {
            transaction = session.beginTransaction();
            int idPermiso = (int) session.save(permiso);
            permiso.setId(idPermiso);
            transaction.commit();
            result = Either.right(permiso);
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(ex.getMessage(), ex);
            result = Either.left(ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
