package dao.dao_implementations.jdbc;

import dao.dao_implementations.jdbc.utils.SqlQueries;
import dao.interfaces.DAOItems;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDaoItems implements DAOItems {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement2;
    private DBConnection dbConnection;

    @Override
    public Item get(int id) {
        prepareCall();

        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ITEM_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
            return null;
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public List<Item> getAll() {
        prepareCall();
        try {
            List<Item> toReturn = new ArrayList<>();
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_ITEM);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Item item = new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
                toReturn.add(item);
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
    public boolean save(Item t) {
        prepareCall();
        if (get(t.getIdItem()) != null){
            return false;
        }
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.INSERT_ITEMS);
            preparedStatement.setString(1, t.getName());
            preparedStatement.setString(2, t.getCompany());
            preparedStatement.setFloat(3, (float)t.getPrice());

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
    public boolean update(Item t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_ITEM);
            preparedStatement.setString(1, t.getName());
            preparedStatement.setString(2, t.getCompany());
            preparedStatement.setFloat(3, (float) t.getPrice());
            preparedStatement.setInt(4, t.getIdItem());

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
    public boolean delete(Item t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_ITEM);
            preparedStatement.setInt(1, t.getIdItem());
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
        preparedStatement2 = null;
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
