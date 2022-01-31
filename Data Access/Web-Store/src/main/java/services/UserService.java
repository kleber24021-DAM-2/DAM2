package services;

import dao.interfaces.DAOUsers;
import dao.mongo.DaoUsersMongo;
import model.user.User;


public class UserService {
    public User checkUserPassword(User fullUser){
        DAOUsers daoUsers = new DaoUsersMongo();
        return daoUsers.validateUserPassword(fullUser);
    }
}
