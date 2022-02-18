package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityPermisosUbicaciones;

import java.util.List;

public interface DaoPermisosUbicaciones {
    Either<String, List<EntityPermisosUbicaciones>> getPermisosByUbicacion(int idUbicacion);

    Integer getEjercicio5(String ubiName);

}
