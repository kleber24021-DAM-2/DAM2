package org.quevedo.secretkeeper.dao.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.quevedo.secretkeeper.config.Configuration;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.sql.DataSource;

public class SqlConnectionProducers {

    @Singleton
    @Produces
    public DataSource getDataSource(Configuration config) {
        config.loadConfig();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getDbUrl());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.addDataSourceProperty(DaoConsts.CACHE_PREP_STMTS, DaoConsts.CACHE_PREP_STMTS_VALUE);
        hikariConfig.addDataSourceProperty(DaoConsts.PREP_STMT_CACHE_SIZE, DaoConsts.PREP_STMT_CACHE_SIZE_VALUE);
        hikariConfig.addDataSourceProperty(DaoConsts.PREP_STMT_CACHE_SQL_LIMIT, DaoConsts.PREP_STMT_CACHE_SQL_LIMIT_VALUE);
        return new HikariDataSource(hikariConfig);
    }
}
