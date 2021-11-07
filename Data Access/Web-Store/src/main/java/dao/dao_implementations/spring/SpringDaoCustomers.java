package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOCustomers;
import model.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SpringDaoCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnPool.getInstance().getPool());
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_CUSTOMERS, BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public boolean save(Customer t) {
        return false;
    }

    @Override
    public void update(Customer t) {

    }

    @Override
    public boolean delete(Customer t) {
        return false;
    }
}
