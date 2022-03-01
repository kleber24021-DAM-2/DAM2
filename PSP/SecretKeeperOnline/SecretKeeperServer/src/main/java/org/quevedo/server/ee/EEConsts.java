package org.quevedo.server.ee;

public class EEConsts {
    public static final String KEY_PROVIDER = "KeyProvider";
    public static final String USER = "User";
    public static final String GROUPS = "Groups";
    public static final String ERROR = "ERROR";
    public static final String APP_PATH = "/api";
    public static final String USER_PATH = "/user";
    public static final String CHECK_PATH = "/check";
    public static final String GET_USER_PATH = "/get";
    public static final String USERNAME = "username";
    public static final String CERT_HEADER = "Cert";
    public static final String NULL = "null";
    public static final String BEARER_HEADER = "Bearer";
    public static final String EL_TIEMPO_DEL_TOKEN_HA_EXPIRADO = "El tiempo del token ha expirado";
    public static final String EL_TOKEN_HA_SIDO_MODIFICADO = "El token ha sido modificado";
    public static final String LA_FIRMA_DEL_TOKEN_NO_ES_VALIDA = "La firma del token no es valida";
    public static final String ISSUER_INCORRECTO = "Issuer incorrecto";
    public static final String FIRMA_INCORRECTA = "Firma incorrecta";

    private EEConsts(){}
    public static final String QUERY_PARAM_USERNAME = "username";
    public static final String QUERY_PARAM_PUBLICKEY = "publickey";
}
