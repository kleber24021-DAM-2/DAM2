package services;

import dao.interfaces.DAOUsers;
import model.user.FullUser;
import model.user.SafeUser;

public class UserService {
    public SafeUser checkUserPassword(FullUser fullUser){
        DAOUsers daoUsers = DaoFactory.getInstance().getDaoUsers();
        return daoUsers.validateUserPassword(fullUser);
    }

    public void updateUser(FullUser updatedUser) {
        DAOUsers daoUsers = DaoFactory.getInstance().getDaoUsers();
        daoUsers.update(updatedUser);
    }
}
