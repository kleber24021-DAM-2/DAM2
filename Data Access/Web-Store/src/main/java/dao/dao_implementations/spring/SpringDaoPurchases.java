package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOPurchases;
import model.Customer;
import model.Item;
import model.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SpringDaoPurchases implements DAOPurchases {
    @Override
    public Purchase get(int id) {
        return null;
    }

    @Override
    public List<Purchase> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnPool.getInstance().getPool());
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_PURCHASES, (rs, rowNum) ->
                getPurchaseFromResultSet(rs));
    }

    @Override
    public void save(Purchase t) {

    }

    @Override
    public void update(Purchase t) {

    }

    @Override
    public void delete(Purchase t) {

    }

    @Override
    public List<Purchase> getByCustomerId(int idCustomer) {
        return null;
    }

    @Override
    public List<Purchase> getByDate(LocalDate selectedDate) {
        return null;
    }

    @Override
    public void deleteByCustomerId(int idCustomer) {

    }

    @Override
    public void deleteByItemId(int idItem) {

    }

    private Purchase getPurchaseFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item(rs.getInt("ITEMS.ID_ITEM"), rs.getString("ITEMS.NAME"), rs.getString("ITEMS.COMPANY"), rs.getDouble("ITEMS.PRICE"));
        Customer customer = new Customer(rs.getInt("CUSTOMERS.ID_CUSTOMER"), rs.getString("CUSTOMERS.NAME"), rs.getString("CUSTOMERS.TELEPHONE"), rs.getString("CUSTOMERS.ADDRESS"));
        return new Purchase(rs.getInt("PURCHASES.ID_PURCHASE"), rs.getDate("PURCHASES.DATE").toLocalDate(), customer, item);
    }
}
