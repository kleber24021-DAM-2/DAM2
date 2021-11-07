package dao.daofactories;

import dao.dao_implementations.file.FileDaoReview;
import dao.dao_implementations.jdbc.JdbcDaoReview;
import dao.dao_implementations.spring.SpringDaoReviews;
import dao.interfaces.DAOReviews;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoFactoryReviews {
    private static DaoFactoryReviews instance;
    private Properties daoProps;

    public DaoFactoryReviews(){
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
}
