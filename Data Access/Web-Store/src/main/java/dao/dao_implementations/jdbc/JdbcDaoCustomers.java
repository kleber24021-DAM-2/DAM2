package dao.dao_implementations.jdbc;

import dao.interfaces.DAOCustomers;
import model.Customer;

import java.util.List;

public class JdbcDaoCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public boolean save(Customer t) {
        return false;
    }

    @Override
    public void update(Customer t) {

    }

    @Override
    public boolean delete(Customer t) {
        return false;
    }
}
