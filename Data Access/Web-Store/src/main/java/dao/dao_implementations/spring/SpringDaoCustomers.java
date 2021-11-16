package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOCustomers;
import model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SpringDaoCustomers implements DAOCustomers {
    @Override
    public Customer get(int id) {
        Customer toReturn;
        JdbcTemplate jdbcTemplate = getTemplate();
        try {
            toReturn = jdbcTemplate.queryForObject(SqlQueries.SELECT_CUSTOMER_BY_ID, new CustomerMapper(), id);
        }catch (EmptyResultDataAccessException emptyData){
            toReturn = null;
        }
        return toReturn;
    }

    @Override
    public List<Customer> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_CUSTOMERS, new CustomerMapper());
    }

    @Override
    public Customer save(Customer t) {
        //I create this final customer because lambda expressions requiere that any variable used in them has to be final or effectively final
        final Customer copyCustomer = new Customer(t.getIdCustomer(), t.getName(),t.getPhone(), t.getAddress());
        //Keyholder to get the generated ID at the creation of the user
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //We create everything needed to make transactions with Spring
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getPool());
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

        try {
            JdbcTemplate template = new JdbcTemplate(transactionManager.getDataSource());
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, (copyCustomer.getName()));
                //The created user has a default password (the name), that can be changed later
                ps.setString(2, copyCustomer.getName());
                return ps;
            }, keyHolder);
            //We modify the id of the customer
            t.setIdCustomer(keyHolder.getKey().intValue());
            template.update(SqlQueries.INSERT_CUSTOMERS, t.getIdCustomer(),t.getName(), t.getPhone(), t.getAddress());
            transactionManager.commit(status);
        }catch (Exception e){
            transactionManager.rollback(status);
        }
        //In case the transaction hasn't completed we give back a null
        if (!status.isCompleted()){
            t = null;
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
        //We create everything needed to make transactions with Spring
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(DBConnPool.getInstance().getPool());
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            JdbcTemplate template = new JdbcTemplate(transactionManager.getDataSource());
            //First query of the transaction
            template.update(SqlQueries.DELETE_CUSTOMER, t.getIdCustomer());
            //Second query of the transaction
            template.update(SqlQueries.DELETE_USER, t.getIdCustomer());
            transactionManager.commit(status);
            resultOfDelete = true;
        }catch (Exception e){
            transactionManager.rollback(status);
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
