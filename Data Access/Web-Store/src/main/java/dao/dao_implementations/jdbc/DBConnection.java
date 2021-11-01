package dao.dao_implementations.jdbc;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection getConnection() {
        Connection conn = null;
        conn = DBConnPool.getInstance().getConnection();
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
