package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOReviews;
import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SpringDaoReviews implements DAOReviews {
    @Override
    public Review get(int id) {
        return null;
    }

    @Override
    public List<Review> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnPool.getInstance().getPool());
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_REVIEWS, (rs, rowNum) ->
                getReviewFromResultSet(rs));
    }

    @Override
    public void save(Review t) {

    }

    @Override
    public void update(Review t) {

    }

    @Override
    public void delete(Review t) {

    }

    private Review getReviewFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item(rs.getInt("P.ID_ITEM"), rs.getString("I.NAME"), rs.getString("COMPANY"), rs.getDouble("PRICE"));
        Customer customer = new Customer(rs.getInt("P.ID_CUSTOMER"), rs.getString("C.NAME"), rs.getString("TELEPHONE"), rs.getString("ADDRESS"));
        Purchase purchase = new Purchase(rs.getInt("REVIEWS.ID_PURCHASE"), rs.getDate("P.DATE").toLocalDate(), customer, item);
        return new Review(rs.getInt("ID_REVIEW"), rs.getInt("RATING"), rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getDate("REVIEWS.DATE").toLocalDate(),purchase);
    }
}
