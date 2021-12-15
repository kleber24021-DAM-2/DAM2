package dao.dao_implementations.hibernate;

import dao.interfaces.DAOItems;
import model.Item;

import java.util.List;

public class DaoItemsHibernate implements DAOItems {
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item save(Item t) {
        return null;
    }

    @Override
    public boolean update(Item t) {
        return false;
    }

    @Override
    public boolean delete(Item t) {
        return false;
    }

    @Override
    public void closePool() {

    }
}
