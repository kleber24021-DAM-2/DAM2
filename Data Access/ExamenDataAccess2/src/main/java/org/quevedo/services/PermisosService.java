package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementation.hibernate.DaoHibernatePermisos;
import org.quevedo.dao.interfaces.DaoPermisos;
import org.quevedo.models.hibernate.EntityPermisos;

import java.util.List;

public class PermisosService {
    public Either<String, List<EntityPermisos>> getAllPermisos(){
        DaoPermisos daoPermisos = new DaoHibernatePermisos();
        return daoPermisos.getAllPermisos();
    }
}
