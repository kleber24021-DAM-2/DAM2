package org.quevedo.dao.interfaces;

import io.vavr.control.Either;

import java.util.List;

public interface DaoObject2 {
    Either<String, List<Void>> getAllObjects2();

    Either<String, Void> getObject2ById();

    Either<String, Void> addNewObject2();

    Either<String, Void> updateObject2();

    Either<String, Void> deleteObject2();
}
