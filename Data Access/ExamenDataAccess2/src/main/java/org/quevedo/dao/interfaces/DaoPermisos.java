package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityPermisos;

import java.util.List;

public interface DaoPermisos {
    Either<String, List<EntityPermisos>> getAllPermisos();
}
