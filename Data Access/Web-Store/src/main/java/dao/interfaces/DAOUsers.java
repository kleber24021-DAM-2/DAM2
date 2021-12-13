package dao.interfaces;


import model.hibernatemodels.EntityUsers;

import java.util.List;

public interface DAOUsers {
    EntityUsers get(int id);

    List<EntityUsers> getAll();

    EntityUsers save(EntityUsers t);

    void update(EntityUsers t);

    boolean delete(EntityUsers t);

    EntityUsers validateUserPassword(String user, String password);
}
