package dao.daofactories;

import dao.dao_implementations.file.XMLDaoCustomers;
import dao.dao_implementations.jdbc.JdbcDaoCustomers;
import dao.dao_implementations.spring.SpringDaoCustomers;
import dao.interfaces.DAOCustomers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFactoryCustomers {
    private static DaoFactoryCustomers instance;
    private Properties daoProps;

    public DaoFactoryCustomers(){
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
}
