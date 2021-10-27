package dao.daofactories;

import dao.dao_implementations.file.NioDAOPurchases;
import dao.dao_implementations.jdbc.JdbcDaoPurchases;
import dao.interfaces.DAOPurchases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFactoryPurchases {
    private static DaoFactoryPurchases instance;
    private Properties daoProps;

    public DaoFactoryPurchases(){
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

    public DAOPurchases getDaoPurchases(){
        DAOPurchases dao = null;
        String sourceProp = daoProps.getProperty("daoPurchases");
        if (sourceProp.equals("JDBC")){
            dao = new JdbcDaoPurchases();
        }else if(sourceProp.equals("Files")){
            dao = new NioDAOPurchases();
        }
        return dao;
    }
}
