package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOCustomers;
import model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SpringDaoCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.queryForObject(SqlQueries.SELECT_CUSTOMER_BY_ID, new CustomerMapper(), id);
    }

    @Override
    public List<Customer> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_CUSTOMERS, new CustomerMapper());
    }

    @Override
    public Customer save(Customer t) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_CUSTOMERS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getName());
            ps.setString(2, t.getPhone());
            ps.setString(3, t.getAddress());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null){
            t.setIdCustomer(keyHolder.getKey().intValue());
        }
        return t;
    }

    @Override
    public void update(Customer t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.UPDATE_CUSTOMER,
                t.getName(), t.getPhone(), t.getAddress(), t.getIdCustomer());
    }

    @Override
    public boolean delete(Customer t) {
        boolean resultOfDelete = false;
        JdbcTemplate jdbcTemplate = getTemplate();
        int affectedRows = jdbcTemplate.update(SqlQueries.DELETE_CUSTOMER, t.getIdCustomer());
        if (affectedRows > 0){
            resultOfDelete = true;
        }
        return resultOfDelete;
    }

    private static final class CustomerMapper implements RowMapper<Customer>{
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                    rs.getInt("ID_CUSTOMER"),
                    rs.getString("NAME"),
                    rs.getString("TELEPHONE"),
                    rs.getString("ADDRESS")
            );
        }
    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }
}
