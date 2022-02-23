package org.quevedo.server.dao.utils;

import java.time.format.DateTimeFormatter;

public class DaoConstants {
    public static final String SELECT_FROM_USUARIOS = "select * from usuarios";
    public static final String CREATE_USER = "insert into usuarios (correo, hashedPassword, codActivacion, isActivo, fechaLimiteActivacion, tipoUsuario) " +
            "values (?, ?, ?, ?, ?, ?)";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String COUNT_USER_BY_CORREO = "select count(*) from usuarios where correo = ?";
    public static final String MSG_USER_NOT_FOUND = "No se encuentra el usuario";
    public static final String MSG_DB_FAILED = "No se ha podido conectar a la base de datos";
    public static final String SELECT_FROM_USUARIOS_BY_COD = "select * from usuarios where codActivacion = ?";
    public static final String UPDATE_USUARIOS_ACTIVATE_BY_ID = "update usuarios set isActivo = true where id = ?";
    public static final String DELETE_USUARIOS_BY_ID = "delete from usuarios where id = ?";
    public static final String UPDATE_USUARIOS_CHANGE_PASSWORD_BY_ID = "update usuarios set hashedPassword = ? where id = ?";
    public static final String UPDATE_USUARIOS_START_PASSWORD_CHANGE = "update usuarios set codActivacion = ?, fechaLimiteActivacion = ? where correo = ?";
    public static final String SELECT_USUARIO_BY_CORREO = "select * from usuarios where correo = ?";
    public static final String SELECT_ALL_PARTIDOS = "select p.idPartido, p.idJornada, j.fecha, p.idLocal, e.nombre, p.idVisitante, e2.nombre, p.resultadoLocal, p.resultadoVisitante from partidos as p join equipos e on e.idEquipo = p.idLocal join equipos e2 on e2.idEquipo = p.idVisitante join jornadas j on j.idJornada = p.idJornada";
    public static final String SELECT_PARTIDOS_BY_EQUIPO = "select p.idPartido, p.idJornada, j.fecha, p.idLocal, e.nombre, p.idVisitante, e2.nombre, p.resultadoLocal, p.resultadoVisitante from partidos as p join equipos e on e.idEquipo = p.idLocal join equipos e2 on e2.idEquipo = p.idVisitante join jornadas j on j.idJornada = p.idJornada where (e.nombre like ? or e2.nombre like ?)";
    public static final String SELECT_PARTIDO_BY_ID = "select p.idPartido, p.idJornada, j.fecha, p.idLocal, e.nombre, p.idVisitante, e2.nombre, p.resultadoLocal, p.resultadoVisitante from partidos as p join equipos e on e.idEquipo = p.idLocal join equipos e2 on e2.idEquipo = p.idVisitante join jornadas j on j.idJornada = p.idJornada where p.idPartido = ?";
    public static final String INSERT_PARTIDO = "insert into partidos (idJornada, idLocal, idVisitante, resultadoLocal, resultadoVisitante) values (?, ?, ?, ?, ?)";
    public static final String UPDATE_PARTIDO_RESULT = "update partidos set resultadoLocal = ?, resultadoVisitante = ? where idPartido = ?";
    public static final String MSG_INTEGRITY_VIOLATION = "Uno de los datos introducidos no figura en la base de datos: \n ";
    public static final String MSG_NO_VALID_ID = "La id introducida no es válida";
    public static final String SELECT_ALL_EQUIPOS = "select * from equipos";
    public static final String INSERT_EQUIPO = "insert into equipos (nombre) values (?)";
    public static final String SELECT_ALL_JORNADAS = "select * from jornadas";
    public static final String INSERT_JORNADA = "insert into jornadas (fecha) values (?)";
    public static final String COL_ID_JORNADA = "idJornada";
    public static final String COL_FECHA = "fecha";
    public static final String PERCENTAGE = "%";
    public static final String COL_ID_PARTIDO = "idPartido";
    public static final String COL_ID_LOCAL = "idLocal";
    public static final String COL_ID_VISITANTE = "idVisitante";
    public static final String COL_RESULTADO_LOCAL = "resultadoLocal";
    public static final String COL_RESULTADO_VISITANTE = "resultadoVisitante";
    public static final String COL_E_NOMBRE = "e.nombre";
    public static final String COL_E_2_NOMBRE = "e2.nombre";
    public static final String COL_ID = "id";
    public static final String COL_CORREO = "correo";
    public static final String COL_HASHED_PASSWORD = "hashedPassword";
    public static final String COL_COD_ACTIVACION = "codActivacion";
    public static final String COL_IS_ACTIVO = "isActivo";
    public static final String COL_FECHA_LIMITE_ACTIVACION = "fechaLimiteActivacion";
    public static final String COL_TIPO_USUARIO = "tipoUsuario";
    public static final String CACHE_PREP_STMTS = "cachePrepStmts";
    public static final String CACHE_PREP_STMTS_VALUE = "true";
    public static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    public static final String PREP_STMT_CACHE_SIZE_VALUE = "250";
    public static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    public static final String PREP_STMT_CACHE_SQL_LIMIT_VALUE = "2048";

    private DaoConstants() {
    }
}
