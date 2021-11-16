package dao.dao_implementations.spring;

import dao.dao_implementations.SqlQueries;
import dao.dbconnections.DBConnPool;
import dao.interfaces.DAOUsers;
import model.user.FullUser;
import model.user.SafeUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SpringDaoUser implements DAOUsers {
    @Override
    public SafeUser get(int id) {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.queryForObject(SqlQueries.SELECT_USER, new SafeUserMapper(), id);
    }

    @Override
    public List<SafeUser> getAll() {
        JdbcTemplate jdbcTemplate = getTemplate();
        return jdbcTemplate.query(SqlQueries.SELECT_ALL_USERS, new SafeUserMapper());
    }
    @Override
    public SafeUser save(FullUser t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con ->{
            PreparedStatement ps = con.prepareStatement(SqlQueries.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getPassword());
            return ps;
        }, keyHolder);
        t.setId(keyHolder.getKey().intValue());
        return t;
    }

    @Override
    public void update(FullUser t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        jdbcTemplate.update(SqlQueries.UPDATE_USER, t.getUsername(), t.getPassword(), t.getId());
    }

    @Override
    public boolean delete(SafeUser t) {
        boolean resultOfDelete = false;
        JdbcTemplate jdbcTemplate = getTemplate();
        int affectedRows = jdbcTemplate.update(SqlQueries.DELETE_USER, t.getId());
        if (affectedRows > 0){
            resultOfDelete = true;
        }
        return resultOfDelete;
    }

    @Override
    public SafeUser validateUserPassword(FullUser t) {
        JdbcTemplate jdbcTemplate = getTemplate();
        SafeUser toReturn = null;
        try {
            toReturn = jdbcTemplate.queryForObject(SqlQueries.CHECK_USER_PASSWORD,
                    new SafeUserMapper(), t.getUsername(), t.getPassword());
        }catch (EmptyResultDataAccessException exception){
            System.out.println("Incorrect login");
        }
       return toReturn;
    }

    private JdbcTemplate getTemplate(){
        return new JdbcTemplate(DBConnPool.getInstance().getPool());
    }

    private static final class SafeUserMapper implements RowMapper<SafeUser>{
        @Override
        public SafeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SafeUser(rs.getInt("USER_ID"), rs.getString("USERNAME"));
        }
    }
}
