/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import io.vavr.control.Either;
import model.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public interface DAOPurchases {

    Either<String, Purchase> get(int id);

    Either<String, List<Purchase>> getAll();

    Either<String, Purchase> save(Purchase t);

    Either<String, Purchase> update(Purchase t);

    Either<String, Void> delete(Purchase t);

    Either<String, List<Purchase>> getByCustomerId(int idCustomer);

    Either<String, List<Purchase>> getByItemId(int idItem);

    Either<String, List<Purchase>> getByDate(LocalDate selectedDate);

    Either<String, List<Purchase>> getSortedByItem();

    Either<String, List<Purchase>> getSortedByCustomer();

    Either<String, List<Purchase>> getInDateRange(LocalDate initialDate, LocalDate finalDate);
}
