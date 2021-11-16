package dao.interfaces;

import model.user.FullUser;
import model.user.SafeUser;

import java.util.List;

public interface DAOUsers {
    SafeUser get(int id);

    List<SafeUser> getAll();

    SafeUser save(FullUser t);

    void update(FullUser t);

    boolean delete(SafeUser t);

    SafeUser validateUserPassword(FullUser t);
}
