package org.quevedo.services;

import io.vavr.control.Either;
import org.quevedo.dao.implementation.hibernate.DaoHibernateObjetos;
import org.quevedo.dao.interfaces.DaoObjeto;
import org.quevedo.models.hibernate.EntityObjeto;

public class ObjetosService {
    public Either<String, EntityObjeto> getObjetoById(int id){
        DaoObjeto daoObjeto = new DaoHibernateObjetos();
        return daoObjeto.getObjetoById(id);
    }
}
