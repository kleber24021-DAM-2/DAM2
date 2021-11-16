package dao.daofactories;

import dao.dao_implementations.file.FileDaoReview;
import dao.dao_implementations.file.IoDAOItems;
import dao.dao_implementations.file.NioDAOPurchases;
import dao.dao_implementations.file.XMLDaoCustomers;
import dao.dao_implementations.jdbc.JdbcDaoCustomers;
import dao.dao_implementations.jdbc.JdbcDaoItems;
import dao.dao_implementations.jdbc.JdbcDaoPurchases;
import dao.dao_implementations.jdbc.JdbcDaoReview;
import dao.dao_implementations.spring.*;
import dao.interfaces.*;

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
        String propertiesFile = "propertiesFiles/settings.xml";
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
        String sourceProp = daoProps.getProperty("daoCustomers");
        switch (sourceProp){
            case "JDBC":
                dao = new JdbcDaoCustomers();
                break;
            case "Files":
                dao = new XMLDaoCustomers();
                break;
            case "Spring":
                dao = new SpringDaoCustomers();
                break;
            default:
                break;
        }
        return dao;
    }

    public DAOItems getDaoItems(){
        DAOItems dao = null;
        String sourceProp = daoProps.getProperty("daoItems");
        switch (sourceProp){
            case "JDBC":
                dao = new JdbcDaoItems();
                break;
            case "Files":
                dao = new IoDAOItems();
                break;
            case "Spring":
                dao = new SpringDaoItems();
                break;
            default:
                break;
        }
        return dao;
    }
    public DAOPurchases getDaoPurchases(){
        DAOPurchases dao = null;
        String sourceProp = daoProps.getProperty("daoPurchases");
        switch (sourceProp){
            case "JDBC":
                dao = new JdbcDaoPurchases();
                break;
            case "Files":
                dao = new NioDAOPurchases();
                break;
            case "Spring":
                dao = new SpringDaoPurchases();
                break;
            default:
                break;
        }
        return dao;
    }

    public DAOReviews getDaoReviews(){
        DAOReviews dao = null;
        String sourceProp = daoProps.getProperty("daoReviews");
        switch (sourceProp){
            case "JDBC":
                dao = new JdbcDaoReview();
                break;
            case "Files":
                dao = new FileDaoReview();
                break;
            case "Spring":
                dao = new SpringDaoReviews();
                break;
            default:
                break;
        }
        return dao;
    }

    public DAOUsers getDaoUsers(){
        DAOUsers dao = null;
        String sourceProp = daoProps.getProperty("daoUsers");
        switch (sourceProp){
            case "JDBC":
                System.err.println("UserDao with JDBC is not implemented");
                break;
            case "Files":
                System.err.println("UserDao with files is not implemented");
                break;
            case "Spring":
                dao = new SpringDaoUser();
                break;
            default:
                break;
        }
        return dao;
    }
}
