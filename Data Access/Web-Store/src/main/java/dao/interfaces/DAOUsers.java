package dao.interfaces;


import model.user.User;

import java.util.List;

public interface DAOUsers {
    User get(int id);

    List<User> getAll();

    User save(User t);

    void update(User t);

    boolean delete(User t);

    User validateUserPassword(User t);
}
