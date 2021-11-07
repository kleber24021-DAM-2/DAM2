package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOItems;
import model.Item;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SpringDaoItems implements DAOItems {
    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DBConnPool.getInstance().getPool());
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_ITEM, BeanPropertyRowMapper.newInstance(Item.class));
    }

    @Override
    public boolean save(Item t) {
        return false;
    }

    @Override
    public boolean update(Item t) {
        return false;
    }

    @Override
    public boolean delete(Item t) {
        return false;
    }

    @Override
    public void closePool() {

    }
}
