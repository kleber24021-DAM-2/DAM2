package dao.dao_implementations.jdbc;

import dao.interfaces.DAOItems;
import model.Item;

import java.util.List;

public class JdbcDaoItems implements DAOItems {
    //TODO implementar el dao
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public boolean save(Item t) {
        return false;
    }

    @Override
    public boolean update(Item t) {
        return false;
    }

    @Override
    public boolean delete(Item t) {

        return false;
    }
}
