package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOPurchases;
import model.Customer;
import model.Item;
import model.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SpringDaoPurchases implements DAOPurchases {
    @Override
    public Purchase get(int id) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.queryForObject(SqlQueries.SELECT_PURCHASE_BY_ID, new PurchasesMapper(), id);
    }

    @Override
    public List<Purchase> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_PURCHASES, new PurchasesMapper());
    }

    @Override
    public Purchase save(Purchase t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_PURCHASE);
            ps.setDate(1, Date.valueOf(t.getDate()));
            ps.setInt(2, t.getCustomer().getIdCustomer());
            ps.setInt(3, t.getItem().getIdItem());
            return ps;
        }, keyHolder);
        t.setId(keyHolder.getKey().intValue());
        return t;
    }

    @Override
    public void update(Purchase t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.UPDATE_PURCHASE,
                Date.valueOf(t.getDate()), t.getCustomer().getIdCustomer(), t.getItem().getIdItem(), t.getId());
    }

    @Override
    public void delete(Purchase t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.DELETE_PURCHASE, t.getId());
    }

    @Override
    public List<Purchase> getByCustomerId(int idCustomer) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_PURCHASES_BY_CUSTOMER, new PurchasesMapper(), idCustomer);
    }

    @Override
    public List<Purchase> getByDate(LocalDate selectedDate) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_PURCHASE_BY_DATE, new PurchasesMapper(), Date.valueOf(selectedDate));
    }

    @Override
    public void deleteByCustomerId(int idCustomer) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.DELETE_PURCHASE_BY_CUSTOMER, idCustomer);
    }

    @Override
    public void deleteByItemId(int idItem) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.DELETE_PURCHASE_BY_ITEM, idItem);
    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }

    private static final class PurchasesMapper implements RowMapper<Purchase>{
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item(rs.getInt("ITEMS.ID_ITEM"), rs.getString("ITEMS.NAME"), rs.getString("ITEMS.COMPANY"), rs.getDouble("ITEMS.PRICE"));
            Customer customer = new Customer(rs.getInt("CUSTOMERS.ID_CUSTOMER"), rs.getString("CUSTOMERS.NAME"), rs.getString("CUSTOMERS.TELEPHONE"), rs.getString("CUSTOMERS.ADDRESS"));
            return new Purchase(rs.getInt("PURCHASES.ID_PURCHASE"), rs.getDate("PURCHASES.DATE").toLocalDate(), customer, item);
        }
    }
}
