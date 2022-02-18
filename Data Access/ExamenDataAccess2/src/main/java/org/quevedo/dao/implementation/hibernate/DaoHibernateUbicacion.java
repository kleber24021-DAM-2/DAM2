package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoUbicacion;
import org.quevedo.models.hibernate.*;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class DaoHibernateUbicacion implements DaoUbicacion {
    @Override
    public Either<String, EntityUbicaciones> getUbicacionById(int idUbicacion) {
        Either<String, EntityUbicaciones> result;
        Session session = HibernateUtils.getSession();
        try {
            EntityUbicaciones objeto = session.get(EntityUbicaciones.class, idUbicacion);
            result = Either.right(objeto);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result = Either.left(ex.getMessage());
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> removeUbicacionWithoutDependencies(EntityUbicaciones toRemoveUbicacion) {
        Either<String, Void> result;
        Session session = HibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(toRemoveUbicacion);
            transaction.commit();
            result = Either.right(null);
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            result = Either.left(exception.getMessage());
            log.error(exception.getMessage(), exception);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Either<String, Void> removeUbicacionWithDependencies(EntityUbicaciones toRemoveUbicacion) {
        Either<String, Void> result;
        Session session = HibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            List<EntityObjeto> listaObjetos = session.createNamedQuery("get_objects_by_ubicacion", EntityObjeto.class)
                    .setParameter("ubicacion", toRemoveUbicacion.getId())
                    .getResultList();

            listaObjetos.forEach(objeto -> session.createNamedQuery("get_object_permission_by_objeto", EntityPermisosObjetos.class)
                    .setParameter("objeto", objeto.getId())
                    .getResultList()
                    .forEach(session::remove));

            listaObjetos.forEach(session::remove);

            session.createNamedQuery("get_permisos_by_ubicacion", EntityPermisosUbicaciones.class)
                    .setParameter("ubicacion", toRemoveUbicacion.getId())
                    .getResultList()
                    .forEach(session::remove);

            //He hecho esta namedQuery porque no funciona el remove. Aunque la named query tampoco se puede utilizar
            session.createNamedQuery("delete_ubicacion", EntityUbicaciones.class)
                    .setParameter("ubicacion", toRemoveUbicacion.getId())
                    .executeUpdate();
            transaction.commit();
            result = Either.right(null);
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            result = Either.left(exception.getMessage());
            log.error(exception.getMessage(), exception);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<EntityUbicaciones> getAllUbicaciones() {
        List<EntityUbicaciones> result;
        Session session = HibernateUtils.getSession();
        try{
            result = session.createNamedQuery("get_all_ubicaciones", EntityUbicaciones.class).getResultList();
        } catch (Exception e) {
            result = Collections.emptyList();
        }finally {
            session.close();
        }
        return result;
    }
}
