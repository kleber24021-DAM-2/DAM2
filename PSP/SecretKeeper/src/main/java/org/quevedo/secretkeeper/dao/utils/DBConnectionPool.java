package org.quevedo.secretkeeper.dao.utils;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Log4j2
@Getter
@Singleton
public class DBConnectionPool {
    private final DataSource hikariDatasource;

    @Inject
    public DBConnectionPool(DataSource hikariDatasource) {
        this.hikariDatasource = hikariDatasource;
    }

    @Produces
    public JdbcTemplate produceJdbcTemplate() {
        return new JdbcTemplate(hikariDatasource);
    }

    public void closePool() {
        ((HikariDataSource) hikariDatasource).close();
    }
}
