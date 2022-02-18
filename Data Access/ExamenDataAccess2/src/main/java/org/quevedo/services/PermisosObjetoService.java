package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementation.hibernate.DaoHibernatePermisosObjetos;
import org.quevedo.dao.interfaces.DaoPermisosObjetos;
import org.quevedo.models.hibernate.EntityPermisosObjetos;

public class PermisosObjetoService {
    public Either<String, EntityPermisosObjetos> addObjectPermission(EntityPermisosObjetos permiso) {
        DaoPermisosObjetos daoPermisosObjetos = new DaoHibernatePermisosObjetos();
        return daoPermisosObjetos.addPermissionToUser(permiso);
    }
}
