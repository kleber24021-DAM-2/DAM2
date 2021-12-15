package dao.dao_implementations.hibernate;

import dao.interfaces.DAOUsers;
import model.user.FullUser;
import model.user.SafeUser;

import java.util.List;

public class DaoUserHibernate implements DAOUsers {
    @Override
    public SafeUser get(int id) {
        return null;
    }

    @Override
    public List<SafeUser> getAll() {
        return null;
    }

    @Override
    public SafeUser save(FullUser t) {
        return null;
    }

    @Override
    public void update(FullUser t) {

    }

    @Override
    public boolean delete(SafeUser t) {
        return false;
    }

    @Override
    public SafeUser validateUserPassword(FullUser t) {
        return null;
    }
}
