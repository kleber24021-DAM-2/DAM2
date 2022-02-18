package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityPermisosObjetos;

public interface DaoPermisosObjetos {
    Either<String, EntityPermisosObjetos> addPermissionToUser(EntityPermisosObjetos permiso);
}
