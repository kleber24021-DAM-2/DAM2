package dao.dao_implementations.hibernate;

import dao.interfaces.DAOUsers;
import dao.utils.HibernateUtils;
import model.User;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class DaoHibernateUsers implements DAOUsers {
    private Session session;
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
    public User validateUserPassword(User toValidate) {
        session = HibernateUtils.getSession();
        User fetchedUser;
        try {
            fetchedUser = session
                    .createNamedQuery("get_user_by_username", User.class)
                    .setParameter("username", toValidate.getUsername())
                    .getSingleResult();
            if (!fetchedUser.getPassword().equals(toValidate.getPassword())) {
                fetchedUser = null;
            }
        }catch (NoResultException noResultEx){
            fetchedUser = null;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return fetchedUser;

    }
}
