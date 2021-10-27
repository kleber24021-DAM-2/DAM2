package dao.dao_implementations.jdbc;

import dao.interfaces.DAOPurchases;
import model.Purchase;

import java.time.LocalDate;
import java.util.List;

public class JdbcDaoPurchases implements DAOPurchases {
    @Override
    public Purchase get(int id) {
        return null;
    }

    @Override
    public List<Purchase> getAll() {
        return null;
    }

    @Override
    public void save(Purchase t) {

    }

    @Override
    public void update(Purchase t) {

    }

    @Override
    public void delete(Purchase t) {

    }

    @Override
    public List<Purchase> getByCustomerId(int idCustomer) {
        return null;
    }

    @Override
    public List<Purchase> getByDate(LocalDate selectedDate) {
        return null;
    }

    @Override
    public void deleteByCustomerId(int idCustomer) {

    }

    @Override
    public void deleteByItemId(int idItem) {

    }
}
