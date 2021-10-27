package dao.dao_implementations.jdbc;

import configuration.ConfigProperties;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection getConnection() {
        String urlDB = ConfigProperties.getInstance().getProperty("urlDB");
        String userName = ConfigProperties.getInstance().getProperty("user_name");
        String password = ConfigProperties.getInstance().getProperty("password");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlDB, userName, password);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
        return conn;
    }

    public void closeConnection(Connection connArg) {
        System.out.println("Releasing all open resources...");
        try {
            if (connArg != null) {
                connArg.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void releaseResource(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
