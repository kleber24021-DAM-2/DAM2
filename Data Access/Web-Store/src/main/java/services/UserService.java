package services;

import dao.daofactories.DaoFactory;
import dao.interfaces.DAOUsers;
import model.User;


public class UserService {
    public User checkUserPassword(User fullUser){
        DAOUsers daoUsers = DaoFactory.getInstance().getDaoUsers();
        return daoUsers.validateUserPassword(fullUser);
    }
}
