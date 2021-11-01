package dao.dao_implementations.jdbc;

import dao.dao_implementations.jdbc.utils.SqlQueries;
import dao.interfaces.DAOCustomers;
import model.Customer;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDaoCustomers implements DAOCustomers {
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DBConnection dbConnection;
    @Override
    public Customer get(int id) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_CUSTOMER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        prepareCall();
        try {
            List<Customer> toReturn = new ArrayList<>();
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_CUSTOMERS);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                toReturn.add(customer);
            }
            return toReturn;
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return Collections.emptyList();
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public boolean save(Customer customer) {
        if (get(customer.getIdCustomer()) != null){
            return false;
        }
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.INSERT_CUSTOMERS);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getAddress());

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return false;
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void update(Customer customer) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_CUSTOMER);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getIdCustomer());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public boolean delete(Customer t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_CUSTOMER);
            preparedStatement.setInt(1, t.getIdCustomer());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return false;
        }finally {
            releaseAllResources();
        }
    }

    private void prepareCall(){
        preparedStatement = null;
        resultSet = null;
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }
    private void releaseAllResources(){
        dbConnection.releaseResource(resultSet);
        dbConnection.releaseResource(preparedStatement);
        dbConnection.closeConnection(connection);
    }
}
