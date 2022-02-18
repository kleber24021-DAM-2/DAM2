package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityObjeto;

import java.util.List;

public interface DaoObjeto {
    Either<String, EntityObjeto> getObjetoById(int idObjeto);
    Either<String, List<EntityObjeto>> getObjetosByUbicacion(int idUbicacion);
}
