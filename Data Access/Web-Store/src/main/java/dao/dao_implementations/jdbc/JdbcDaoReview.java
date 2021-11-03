package dao.dao_implementations.jdbc;

import dao.dao_implementations.jdbc.utils.SqlQueries;
import dao.interfaces.DAOReviews;
import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDaoReview implements DAOReviews {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DBConnection dbConnection;

    @Override
    public Review get(int id) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_REVIEW_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return getReviewFromResultSet(resultSet);
            }
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
        return null;
    }

    @Override
    public List<Review> getAll() {
        prepareCall();
        try {
            List<Review> toReturn = new ArrayList<>();
            preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_REVIEWS);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Review review = getReviewFromResultSet(resultSet);
                toReturn.add(review);
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
    public void save(Review t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.INSERT_REVIEW);
            preparedStatement.setInt(1, t.getRating().getValue());
            preparedStatement.setString(2, t.getTitle());
            preparedStatement.setString(3, t.getDescription());
            preparedStatement.setDate(4, Date.valueOf(t.getDate()));
            preparedStatement.setInt(5, t.getPurchase().getId());

            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void update(Review t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_REVIEW);
            preparedStatement.setInt(1, t.getRating().getValue());
            preparedStatement.setString(2, t.getTitle());
            preparedStatement.setString(3, t.getDescription());
            preparedStatement.setDate(4, Date.valueOf(t.getDate()));
            preparedStatement.setInt(5, t.getPurchase().getId());
            preparedStatement.setInt(6, t.getIdReview());

            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    @Override
    public void delete(Review t) {
        prepareCall();
        try {
            preparedStatement = connection.prepareStatement(SqlQueries.DELETE_REVIEW);
            preparedStatement.setInt(1, t.getIdReview());
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            Logger.getLogger(getClass().toString()).log(Level.SEVERE, sqlException.getMessage(), sqlException);
        }finally {
            releaseAllResources();
        }
    }

    private Review getReviewFromResultSet(ResultSet resultSet) throws SQLException{
        return new Review(resultSet.getInt("ID_REVIEW"), resultSet.getInt("RATING"), resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"), resultSet.getDate("DATE").toLocalDate(),
                new Purchase(resultSet.getInt("REVIEWS.ID_PURCHASE"), resultSet.getDate("P.DATE").toLocalDate(),
                        new Customer(resultSet.getInt("P.ID_CUSTOMER"), resultSet.getString("C.NAME"), resultSet.getString("TELEPHONE"), resultSet.getString("ADDRESS")),
                        new Item(resultSet.getInt("P.ID_ITEM"), resultSet.getString("I.NAME"), resultSet.getString("COMPANY"), resultSet.getDouble("PRICE"))));
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
