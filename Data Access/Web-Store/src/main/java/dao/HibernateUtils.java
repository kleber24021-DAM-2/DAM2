package dao;

import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Log4j2
public class HibernateUtils {
    private static SessionFactory ourSessionFactory;

    private HibernateUtils(){}

    public static Session getSession() throws HibernateException {
        if (ourSessionFactory == null){
            try {
                Configuration configuration = new Configuration();
                configuration.configure();

                ourSessionFactory = configuration.buildSessionFactory();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return ourSessionFactory.openSession();
    }
}
