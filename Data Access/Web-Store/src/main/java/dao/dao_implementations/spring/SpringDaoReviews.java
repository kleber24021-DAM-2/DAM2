package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOReviews;
import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class SpringDaoReviews implements DAOReviews {
    @Override
    public Review get(int id) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.queryForObject(SqlQueries.SELECT_REVIEW_BY_ID, new ReviewsMapper(), id);
    }

    @Override
    public List<Review> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_REVIEWS, new ReviewsMapper());
    }

    @Override
    public Review save(Review t) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_REVIEW, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getRating().getValue());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getDate()));
            ps.setInt(5, t.getPurchase().getId());
            return ps;
        }, keyHolder);
        t.setIdReview(keyHolder.getKey().intValue());
        return t;
    }

    @Override
    public void update(Review t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.UPDATE_REVIEW,
                t.getRating().getValue(), t.getTitle(), t.getDescription(), Date.valueOf(t.getDate()), t.getPurchase().getId(), t.getIdReview());
    }

    @Override
    public void delete(Review t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.DELETE_REVIEW, t.getIdReview());
    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }

    private static final class ReviewsMapper implements RowMapper<Review>{

        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item(rs.getInt("P.ID_ITEM"), rs.getString("I.NAME"), rs.getString("COMPANY"), rs.getDouble("PRICE"));
            Customer customer = new Customer(rs.getInt("P.ID_CUSTOMER"), rs.getString("C.NAME"), rs.getString("TELEPHONE"), rs.getString("ADDRESS"));
            Purchase purchase = new Purchase(rs.getInt("REVIEWS.ID_PURCHASE"), rs.getDate("P.DATE").toLocalDate(), customer, item);
            return new Review(rs.getInt("ID_REVIEW"), rs.getInt("RATING"), rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getDate("REVIEWS.DATE").toLocalDate(),purchase);
        }
    }
}
