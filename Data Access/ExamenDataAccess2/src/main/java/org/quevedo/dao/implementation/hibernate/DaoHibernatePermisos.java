package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import org.hibernate.Session;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoPermisos;
import org.quevedo.models.hibernate.EntityPermisos;

import java.util.List;

public class DaoHibernatePermisos implements DaoPermisos {
    @Override
    public Either<String, List<EntityPermisos>> getAllPermisos() {
        Either<String, List<EntityPermisos>> result;
        Session session = HibernateUtils.getSession();
        try{
            result = Either.right(session.createNamedQuery("get_all_permisos", EntityPermisos.class).getResultList());
        } catch (Exception e) {
            result = Either.left(e.getMessage());
        }finally {
            session.close();
        }
        return result;
    }
}
