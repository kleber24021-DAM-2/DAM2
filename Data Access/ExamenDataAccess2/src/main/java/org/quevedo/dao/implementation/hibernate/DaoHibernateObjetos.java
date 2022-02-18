package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoObjeto;
import org.quevedo.models.hibernate.EntityObjeto;
import org.quevedo.models.hibernate.EntityPermisosUbicaciones;

import java.util.List;

@Log4j2
public class DaoHibernateObjetos implements DaoObjeto {
    @Override
    public Either<String, EntityObjeto> getObjetoById(int idObjeto) {
        Either<String, EntityObjeto> result;
        Session session = HibernateUtils.getSession();
        try{
            EntityObjeto objeto = session.get(EntityObjeto.class, idObjeto);
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
    public Either<String, List<EntityObjeto>> getObjetosByUbicacion(int idUbicacion) {
        Either<String, List<EntityObjeto>> result;
        Session session = HibernateUtils.getSession();
        try {
            result = Either.right(
                    session.createNamedQuery("get_objects_by_ubicacion", EntityObjeto.class)
                            .setParameter("ubicacion", idUbicacion)
                            .getResultList()
            );
        } catch (Exception e) {
            result = Either.left(e.getMessage());
        }finally {
            session.close();
        }
        return result;
    }
}
