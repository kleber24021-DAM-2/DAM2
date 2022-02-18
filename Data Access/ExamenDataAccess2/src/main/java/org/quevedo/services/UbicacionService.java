package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementation.hibernate.DaoHibernateObjetos;
import org.quevedo.dao.implementation.hibernate.DaoHibernatePermisosUbicaciones;
import org.quevedo.dao.implementation.hibernate.DaoHibernateUbicacion;
import org.quevedo.dao.interfaces.DaoObjeto;
import org.quevedo.dao.interfaces.DaoPermisosUbicaciones;
import org.quevedo.dao.interfaces.DaoUbicacion;
import org.quevedo.models.hibernate.EntityObjeto;
import org.quevedo.models.hibernate.EntityPermisosUbicaciones;
import org.quevedo.models.hibernate.EntityUbicaciones;

import java.util.List;

public class UbicacionService {
    public Either<String, EntityUbicaciones> getUbicacionById(int id) {
        DaoUbicacion daoUbicacion = new DaoHibernateUbicacion();
        return daoUbicacion.getUbicacionById(id);
    }

    public Either<String, String> deleteUbicacion(EntityUbicaciones toDelete) {
        DaoUbicacion daoUbicacion = new DaoHibernateUbicacion();
        DaoObjeto daoObjeto = new DaoHibernateObjetos();
        DaoPermisosUbicaciones daoPermisosUbicaciones = new DaoHibernatePermisosUbicaciones();

        List<EntityObjeto> listaObjetos = daoObjeto.getObjetosByUbicacion(toDelete.getId()).get();
        List<EntityPermisosUbicaciones> listaPermisosUbicaciones = daoPermisosUbicaciones.getPermisosByUbicacion(toDelete.getId()).get();

        Either<String, String> result;

        if (listaObjetos.isEmpty() && listaPermisosUbicaciones.isEmpty()) {
            Either<String, Void> result1 = daoUbicacion.removeUbicacionWithoutDependencies(toDelete);
            if (result1.isRight()) {
                result = Either.right("Removed. Didnt have any associated permission or object");
            } else {
                result = Either.left(result1.getLeft());
            }
        } else {
            Either<String, Void> result1 = daoUbicacion.removeUbicacionWithDependencies(toDelete);
            if (result1.isRight()) {
                result = Either.right("Removed. All the associated permissions and objects have being removed too");
            } else {
                result = Either.left(result1.getLeft());
            }
        }
        return result;
    }

    public List<EntityUbicaciones> getAllUbicaciones() {
        DaoUbicacion daoUbicacion = new DaoHibernateUbicacion();
        return daoUbicacion.getAllUbicaciones();
    }
}
