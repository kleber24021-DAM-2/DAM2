package org.quevedo.dao.interfaces;

import io.vavr.control.Either;

import java.util.List;

public interface DaoObject3 {
    Either<String, List<Void>> getAllObjects3();

    Either<String, Void> getObject3ById();

    Either<String, Void> addNewObject3();

    Either<String, Void> updateObject3();

    Either<String, Void> deleteObject3();
}
