package dao.daofactories;

import dao.dao_implementations.file.IoDAOItems;
import dao.dao_implementations.jdbc.JdbcDaoItems;
import dao.dao_implementations.spring.SpringDaoItems;
import dao.interfaces.DAOItems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFactoryItems {
    private static DaoFactoryItems instance;
    private Properties daoProps;

    public DaoFactoryItems(){
        String PROPERTIES_FILE = "propertiesFiles/settings.xml";
        setDaoType(PROPERTIES_FILE);
    }

    private void setDaoType(String configFile){
        daoProps = new Properties();
        try {
            daoProps.loadFromXML(Files.newInputStream(Paths.get(configFile)));
        } catch (IOException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
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
}
