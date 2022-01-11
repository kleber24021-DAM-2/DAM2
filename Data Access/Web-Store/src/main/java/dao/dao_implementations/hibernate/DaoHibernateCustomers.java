package dao.dao_implementations.hibernate;

import dao.interfaces.DAOCustomers;
import model.Customer;

import java.util.List;

public class DaoHibernateCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public Customer save(Customer t) {
        return null;
    }

    @Override
    public boolean update(Customer t) {
        return false;
    }

    @Override
    public boolean delete(Customer t) {
        return false;
    }
}
