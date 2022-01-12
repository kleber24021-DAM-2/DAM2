package dao.daofactories;
import dao.dao_implementations.hibernate.*;
import dao.interfaces.*;
import dao.utils.DaoConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFactory {
    private static DaoFactory instance;
    private Properties daoProps;

    private DaoFactory(){
        String propertiesFile = DaoConstants.PROPERTIES_PATH;
        setDaoType(propertiesFile);
    }

    private void setDaoType(String configFile){
        daoProps = new Properties();
        try {
            daoProps.loadFromXML(Files.newInputStream(Paths.get(configFile)));
        } catch (IOException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static DaoFactory getInstance(){
        if (instance == null){
            instance = new DaoFactory();
        }
        return instance;
    }

    public DAOCustomers getDaoCustomers(){
        DAOCustomers dao = null;
        String sourceProp = daoProps.getProperty(DaoConstants.DAO_CUSTOMER_STRING);
        if (DaoConstants.HIBERNATE_STRING.equals(sourceProp)) {
            dao = new DaoHibernateCustomers();
        }
        return dao;
    }

    public DAOItems getDaoItems(){
        DAOItems dao = null;
        String sourceProp = daoProps.getProperty(DaoConstants.DAO_ITEM_STRING);
        if (DaoConstants.HIBERNATE_STRING.equals(sourceProp)) {
            dao = new DaoHibernateItems();
        }
        return dao;
    }
    public DAOPurchases getDaoPurchases(){
        DAOPurchases dao = null;
        String sourceProp = daoProps.getProperty(DaoConstants.DAO_PURCHASE_STRING);
        if (DaoConstants.HIBERNATE_STRING.equals(sourceProp)) {
            dao = new DaoHibernatePurchases();
        }
        return dao;
    }

    public DAOReviews getDaoReviews(){
        DAOReviews dao = null;
        String sourceProp = daoProps.getProperty(DaoConstants.DAO_REVIEW_STRING);
        if (DaoConstants.HIBERNATE_STRING.equals(sourceProp)) {
            dao = new DaoHibernateReviews();
        }
        return dao;
    }

    public DAOUsers getDaoUsers(){
        DAOUsers dao = null;
        String sourceProp = daoProps.getProperty(DaoConstants.DAO_USER_STRING);
        if (DaoConstants.HIBERNATE_STRING.equals(sourceProp)) {
            dao = new DaoHibernateUsers();
        }
        return dao;
    }
}
