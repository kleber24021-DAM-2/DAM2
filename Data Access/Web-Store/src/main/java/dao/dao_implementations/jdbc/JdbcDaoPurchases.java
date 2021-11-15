package dao.dao_implementations.jdbc;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnection;
import dao.interfaces.DAOPurchases;
import model.Customer;
import model.Item;
import model.Purchase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDaoPurchases implements DAOPurchases {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DBConnection dbConnection;

    @Override
    public Purchase get(int id) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_PURCHASE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Customer customer = new Customer(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                Item item = new Item(resultSet.getInt(7), resultSet.getString(8), resultSet.getString(9), resultSet.getFloat(10));
                return new Purchase(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), customer, item);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        } finally {
            releaseAllResources();
        }
        return null;
    }

    @Override
    public List<Purchase> getAll() {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_PURCHASES);
            resultSet = preparedStatement.executeQuery();
            return getPurchasesList(resultSet);
        } catch (SQLException sqlException) {
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        } finally {
            releaseAllResources();
        }
        return Collections.emptyList();
    }

    @Override
    public Purchase save(Purchase t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.INSERT_PURCHASE);
            preparedStatement.setDate(1, java.sql.Date.valueOf(t.getDate()));
            preparedStatement.setInt(2, t.getCustomer().getIdCustomer());
            preparedStatement.setInt(3, t.getItem().getIdItem());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void update(Purchase t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_PURCHASE);
            preparedStatement.setDate(1, java.sql.Date.valueOf(t.getDate()));
            preparedStatement.setInt(2, t.getCustomer().getIdCustomer());
            preparedStatement.setInt(3, t.getItem().getIdItem());
            preparedStatement.setInt(4, t.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void delete(Purchase t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_PURCHASE);
            preparedStatement.setInt(1, t.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public List<Purchase> getByCustomerId(int idCustomer) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_PURCHASES_BY_CUSTOMER);
            preparedStatement.setInt(1, idCustomer);
            resultSet = preparedStatement.executeQuery();
            return getPurchasesList(resultSet);
        } catch (SQLException sqlException) {
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        } finally {
            releaseAllResources();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Purchase> getByDate(LocalDate selectedDate) {
        List<Purchase> toReturn;
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_PURCHASE_BY_DATE);
            preparedStatement.setDate(1, Date.valueOf(selectedDate));
            resultSet = preparedStatement.executeQuery();
            toReturn = getPurchasesList(resultSet);
            return toReturn;
        } catch (SQLException sqlException) {
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        } finally {
            releaseAllResources();
        }
        return null;
    }

    @Override
    public void deleteByCustomerId(int idCustomer) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_PURCHASE_BY_CUSTOMER);
            preparedStatement.setInt(1, idCustomer);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void deleteByItemId(int idItem) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_PURCHASE_BY_ITEM);
            preparedStatement.setInt(1, idItem);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
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
    private List<Purchase> getPurchasesList(ResultSet resultSet) throws SQLException{
        List<Purchase> toReturn = new ArrayList<>();
        while (resultSet.next()){
            Customer customer = new Customer(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            Item item = new Item(resultSet.getInt(7), resultSet.getString(8), resultSet.getString(9), resultSet.getFloat(10));
            toReturn.add(new Purchase(resultSet.getInt(1), resultSet.getDate(2).toLocalDate(), customer, item));
        }
        return toReturn;
    }
}
