package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOUsers;
import model.Item;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SpringDaoUser implements DAOUsers {
//    @Override
//    public boolean checkUserPassword(String username, String password) {
//        JdbcTemplate jdbcTemplate = getTemplate();
//        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SqlQueries.CHECK_USER_PASSWORD,
//                (rs, rowNum) -> rs.getBoolean(1)
//                , username, password, username));
//    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }

    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item save(Item t) {
        return null;
    }

    @Override
    public boolean update(Item t) {
        return false;
    }

    @Override
    public boolean delete(Item t) {
        return false;
    }
}
