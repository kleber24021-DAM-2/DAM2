package dao.dao_implementations.jdbc;

import configuration.ConfigProperties;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnPool {
    private static DBConnPool dbConnectionPool = null;
    private BasicDataSource pool = null;
    private String driver;
    private String urlDB;
    private String userName;
    private String password;

    private DBConnPool(){
        super();
        pool = this.getPool();
    }

    public static DBConnPool getInstance() {
        if (dbConnectionPool == null) {
            dbConnectionPool = new DBConnPool();
        }
        return dbConnectionPool;
    }

    private BasicDataSource getPool(){
        driver = ConfigProperties.getInstance().getProperty("driver");
        urlDB = ConfigProperties.getInstance().getProperty("urlDB");
        userName = ConfigProperties.getInstance().getProperty("user_name");
        password = ConfigProperties.getInstance().getProperty("password");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driver);
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(urlDB);

        basicDataSource.setInitialSize(4);

        basicDataSource.setValidationQuery("select 1");

        System.out.println("Pool created");
        return basicDataSource;
    }

    public static void closePool(BasicDataSource dbConnection){
        System.out.println("Releasing all open resources");
        try{
            if (dbConnection != null){
                dbConnection.close();
            }
        }catch (SQLException sqlException){
            Logger.getLogger("closePool").log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
    }

    public Connection getConnection(){
        try{
            return pool.getConnection();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }
        return null;
    }
}
