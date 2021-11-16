package dao.interfaces;

import model.Item;

import java.util.List;

public interface DAOUsers {
    Item get(int id);

    List<Item> getAll();

    Item save(Item t);

    boolean update(Item t);

    boolean delete(Item t);
}
