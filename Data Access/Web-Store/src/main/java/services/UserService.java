package services;

import dao.dao_implementations.DaoUserHibernate;
import dao.interfaces.DAOUsers;
import model.hibernatemodels.EntityUsers;

public class UserService {
    DAOUsers daoUsers = new DaoUserHibernate();
    public EntityUsers checkUserPassword(String user, String password){
        return daoUsers.validateUserPassword(user, password);
    }

    public void updateUser(EntityUsers updatedUser) {
        daoUsers.update(updatedUser);
    }
}
