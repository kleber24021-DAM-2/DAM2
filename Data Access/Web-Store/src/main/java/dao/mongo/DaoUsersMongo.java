package dao.mongo;

import dao.interfaces.DAOUsers;
import model.user.User;

import java.util.List;

public class DaoUsersMongo implements DAOUsers {
    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User t) {
        return null;
    }

    @Override
    public void update(User t) {

    }

    @Override
    public boolean delete(User t) {
        return false;
    }

    @Override
    public User validateUserPassword(User t) {
        return null;
    }
}
