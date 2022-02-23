package org.quevedo.server.dao.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.quevedo.server.config.Configuration;

import javax.sql.DataSource;

public class SqlModule {
    @Singleton
    @Produces
    public DataSource getDataSource(Configuration config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getPath());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.addDataSourceProperty(DaoConstants.CACHE_PREP_STMTS, DaoConstants.CACHE_PREP_STMTS_VALUE);
        hikariConfig.addDataSourceProperty(DaoConstants.PREP_STMT_CACHE_SIZE, DaoConstants.PREP_STMT_CACHE_SIZE_VALUE);
        hikariConfig.addDataSourceProperty(DaoConstants.PREP_STMT_CACHE_SQL_LIMIT, DaoConstants.PREP_STMT_CACHE_SQL_LIMIT_VALUE);
        return new HikariDataSource(hikariConfig);
    }
}
