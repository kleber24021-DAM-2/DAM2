package org.quevedo.dao.interfaces;

import io.vavr.control.Either;
import org.quevedo.models.hibernate.EntityUsuarios;

import java.util.List;

public interface DaoUsuarios {
    Either<String, EntityUsuarios> getUsuarioById(int getUsuarioId);
    Either<String, Void> removeUserById(int removeUsuarioId);
}
