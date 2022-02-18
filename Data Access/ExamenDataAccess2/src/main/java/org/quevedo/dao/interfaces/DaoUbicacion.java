package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityUbicaciones;

import java.util.List;

public interface DaoUbicacion {
    Either<String, EntityUbicaciones> getUbicacionById(int idUbicacion);
    Either<String, Void> removeUbicacionWithoutDependencies(EntityUbicaciones toRemoveUbicacion);
    Either<String, Void> removeUbicacionWithDependencies(EntityUbicaciones toRemoveUbicacion);

    List<EntityUbicaciones> getAllUbicaciones();
}
