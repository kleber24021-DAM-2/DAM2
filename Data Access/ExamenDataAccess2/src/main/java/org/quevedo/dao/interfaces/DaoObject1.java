package org.quevedo.dao.interfaces;

import io.vavr.control.Either;

import java.util.List;

public interface DaoObject1 {
    Either<String, List<Void>> getAllObjects1();

    Either<String, Void> getObject1ById();

    Either<String, Void> addNewObject1();

    Either<String, Void> updateObject1();

    Either<String, Void> deleteObject1();
}
