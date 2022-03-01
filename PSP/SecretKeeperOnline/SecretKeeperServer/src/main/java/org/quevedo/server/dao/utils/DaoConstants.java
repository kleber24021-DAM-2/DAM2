package org.quevedo.server.dao.utils;

public class DaoConstants {
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String CACHE_PREP_STMTS_VALUE = "true";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SIZE_VALUE = "250";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static final String PREP_STMT_CACHE_SQL_LIMIT_VALUE = "2048";
    public static final String SELECT_SECRET_BY_USERNAME = "select S.id, message, secretName, signature, secretOwner from Secrets S join Shared_Users SU on S.id = SU.IdSecreto join Users U on U.id = SU.idUsuario where U.nombre = ?";
    public static final String INSERT_SECRET = "insert into Secrets (message, secretName, signature, secretOwner) values (?, ?, ?, ?)";
    public static final String INSERT_SHARED = "insert into Shared_Users (PasswordCifrada, idUsuario, IdSecreto) values (?, ?, ?)";
    public static final String SELECT_PASSWORD = "select PasswordCifrada from Shared_Users where IdSecreto = ? and idUsuario = ?";
    public static final String INSERT_USER = "insert into Users (nombre, publicKey) values (?, ?)";
    public static final String SELECT_USER_BY_NAME = "select * from Users where nombre = ?";
    public static final String SELECT_ALL_USERS = "select * from Users";
    public static final String SELECT_USER_BY_ID = "select * from Users where id = ?";

    private DaoConstants() {
    }
}
