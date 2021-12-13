package dao.dao_implementations;

import dao.interfaces.DAOUsers;
import model.hibernatemodels.EntityUsers;

import java.util.List;

public class DaoUserHibernate implements DAOUsers {
    @Override
    public EntityUsers get(int id) {
        return null;
    }

    @Override
    public List<EntityUsers> getAll() {
        return null;
    }

    @Override
    public EntityUsers save(EntityUsers t) {
        return null;
    }

    @Override
    public void update(EntityUsers t) {

    }

    @Override
    public boolean delete(EntityUsers t) {
        return false;
    }

    @Override
    public EntityUsers validateUserPassword(String user, String password) {
        return null;
    }
}
