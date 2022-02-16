package org.quevedo.secretkeeper.dao.utils;

public class DaoConsts {
    public static final String PATH_CONFIG = "/config/config.yml";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "driver";
    public static final String MSG_DB_FAILED = "No se ha podido conectar a la base de datos";
    public static final String INSERT_SECRET = "insert into Secrets (message, owner) values (?, ?)";
    public static final String MSG_ENCRYPT_FAILED = "Error al encriptar";
    public static final String SELECT_ALL_SECRETS = "select * from Secrets";
    public static final String SELECT_SECRET_BY_ID = "select * from Secrets where id = ?";
    public static final String MSG_DECRYPT_FAILED = "Fallo al desencriptar. Por favor, revise la contraseña o la selección";
    public static final String MSG_SECRET_NULL = "El secreto pedido no existe";
    public static final String DB_URL = "db_url";
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String CACHE_PREP_STMTS_VALUE = "true";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SIZE_VALUE = "250";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static final String PREP_STMT_CACHE_SQL_LIMIT_VALUE = "2048";
    public static final String GENERAL_PASSWORD = "generalPass";

    private DaoConsts() {
    }
}
