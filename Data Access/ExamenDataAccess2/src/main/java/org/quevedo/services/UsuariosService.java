package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementation.hibernate.DaoHibernateUsuarios;
import org.quevedo.dao.interfaces.DaoUsuarios;
import org.quevedo.models.hibernate.EntityUsuarios;

public class UsuariosService {
    public Either<String, EntityUsuarios> getUsuarioById(int id){
        DaoUsuarios daoUsuarios = new DaoHibernateUsuarios();
        return daoUsuarios.getUsuarioById(id);
    }

    public Either<String, Void> removeUsuarioById(int id){
        DaoUsuarios daoUsuarios = new DaoHibernateUsuarios();
        return daoUsuarios.removeUserById(id);
    }
}
