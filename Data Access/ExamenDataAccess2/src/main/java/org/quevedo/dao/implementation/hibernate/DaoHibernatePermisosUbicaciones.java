package org.quevedo.dao.implementation.hibernate;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.quevedo.dao.HibernateUtils;
import org.quevedo.dao.interfaces.DaoPermisosUbicaciones;
import org.quevedo.models.hibernate.EntityPermisos;
import org.quevedo.models.hibernate.EntityPermisosUbicaciones;

import java.util.List;

@Log4j2
public class DaoHibernatePermisosUbicaciones implements DaoPermisosUbicaciones {
    @Override
    public Either<String, List<EntityPermisosUbicaciones>> getPermisosByUbicacion(int idUbicacion) {
        Either<String, List<EntityPermisosUbicaciones>> result;
        Session session = HibernateUtils.getSession();
        try {
            result = Either.right(
                    session.createNamedQuery("get_permisos_by_ubicacion", EntityPermisosUbicaciones.class)
                            .setParameter("ubicacion", idUbicacion)
                            .getResultList()
            );
        } catch (Exception e) {
            result = Either.left(e.getMessage());
            log.error(e.getMessage(), e);
        }finally {
            session.close();
        }
        return result;
    }

    @Override
    public Integer getEjercicio5(String ubiName) {
        Session session = HibernateUtils.getSession();
        Integer result = null;
        try {
            result = session.createNamedQuery("get_num_usuarios_permisos_ubicacion", Long.class)
                    .setParameter("ubicacionName", ubiName)
                    .getResultList().get(0).intValue();
        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
        }finally {
            session.close();
        }
        return result;
    }
}
