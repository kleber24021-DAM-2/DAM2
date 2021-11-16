package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOItems;
import model.Item;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class SpringDaoItems implements DAOItems {
    @Override
    public Item get(int id) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.queryForObject(SqlQueries.SELECT_ITEM_BY_ID, BeanPropertyRowMapper.newInstance(Item.class), id);
    }

    @Override
    public List<Item> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_ITEM, BeanPropertyRowMapper.newInstance(Item.class));
    }

    @Override
    public Item save(Item t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_ITEMS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getName());
            ps.setString(2, t.getCompany());
            ps.setDouble(3, t.getPrice());
            return ps;
        }, keyHolder);
        t.setIdItem(keyHolder.getKey().intValue());
        return t;
    }

    @Override
    public boolean update(Item t) {
        boolean resultOfUpdate = false;
        JdbcTemplate jdbcTemplate = getTemplate();
        int affectedRow = jdbcTemplate.update(SqlQueries.UPDATE_ITEM,
                t.getName(), t.getCompany(), t.getPrice(),t.getIdItem());
        if (affectedRow > 0){
            resultOfUpdate = true;
        }
        return resultOfUpdate;
    }

    @Override
    public boolean delete(Item t) {
        boolean resultOfDelete = false;
        //We create everything needed to make transactions with Spring
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getPool());
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(transactionManager.getDataSource());
            jdbcTemplate.update(SqlQueries.DELETE_PURCHASE_BY_ITEM, t.getIdItem());
            jdbcTemplate.update(SqlQueries.DELETE_ITEM, t.getIdItem());
            transactionManager.commit(status);
            resultOfDelete = true;
        }catch (Exception e){
            transactionManager.rollback(status);
        }
        return resultOfDelete;
    }

    @Override
    public void closePool() {
        DBConnPool dbConnPool = DBConnPool.getInstance();
        dbConnPool.closePool();
    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }
}
