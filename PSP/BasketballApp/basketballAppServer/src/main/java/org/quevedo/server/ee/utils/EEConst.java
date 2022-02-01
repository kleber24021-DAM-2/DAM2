package org.quevedo.server.ee.utils;

import org.quevedo.sharedmodels.usuario.TipoUsuario;

public class EEConst {
    public static final String PARAM_PASSWORD_CONFIRM = "paramPasswordConfirm";
    public static final String PARAM_PASSWORD = "paramPassword";
    public static final String PATH_ACTIVATE = "/activate";
    public static final String NAME_SERVLET_ACTIVATE = "ServletActivateUser";
    public static final String APP_PATH = "/basketball";
    public static final String PATH_USERS_REST = "/users";
    public static final String COD_ACTIVACION = "codActivacion";
    public static final String ATTRIBUTE_RESULT = "result";
    public static final String ACTIVATION_JSP = "/activation.jsp";
    public static final String COD_PASS = "codCambio";
    public static final String NAME_SERVLET_PASSWORD_CHANGE = "ServletChangePassword";
    public static final String PATH_SERVLET_PASSWORD_CHANGE = "/ServletChangePassword";
    public static final String CHANGE_PASS_JSP = "/changepass.jsp";
    public static final String MSG_PASSWORD_CHANGE = "Si la cuenta existe se enviará un correo de confirmación de cambio de contraseña";
    public static final String PATH_PASSWORD_CHANGE = "/passwordchange";
    public static final String MSG_INTRODUCE_MAIL = "Introduzca el correo para cambiar contraseña";

    //Parametros y mensajes de servlet de login
    public static final String PARAM_CORREO = "correo";
    public static final String CONTENT_TYPE_LOGIN = "application/json";
    public static final String ENCODING = "UTF-8";
    public static final String PARAM_LOGIN_CORREO = "correo";
    public static final String PARAM_LOGIN_PASSWORD = "pass";
    public static final String MSG_CORRECT_LOGIN = "Se ha podido hacer log-in correctamente";
    public static final String ATTRIBUTE_LOGGED = "logged";
    public static final String MSG_WRONG_EMAIL_PASSWORD = "El usuario y/o contraseña introducido no se encuentra en la base de datos o el usuario no está activado";
    public static final String MSG_NOT_LOGGED_INTERNAL_ERROR = "No se ha podido hacer log-in. Por favor, vuelva a intentarlo más tarde";
    public static final String SERVLET_LOGIN_NAME = "ServletLogin";
    public static final String SERVLET_LOGIN_PATH = "/basketball/ServletLogin";
    public static final String PATH_REGULAR_USERS = "/regular";
    public static final String PATH_ADMIN_USERS = "/admin";
    public static final String PATH_REST_PARTIDOS = "/partidos";
    public static final String MSG_BAD_INPUT = "El objeto recibido no coincide con el formato";
    public static final String PATH_REST_EQUIPOS = "/equipos";
    public static final String MSG_EMPTY_QUERY_PARAM = "No ha introducido suficientes parámetros para realizar esta acción";
    public static final String QUERY_PARAM_NOMBRE = "nombre";
    public static final String PATH_ADD = "/add";
    public static final String PATH_RESULT = "/result";
    public static final String PATH_FILTER = "/filter";
    public static final String QUERY_PARAM_EQUIPO = "equipo";
    public static final String QUERY_PARAM_JORNADA = "jornada";
    public static final String MSG_DATE_ERROR = "La fecha introducida no cumple con el formato yyyy-MM-dd";
    public static final String PATH_REST_JORNADAS = "/jornadas";
    public static final String PARAM_FECHA = "fecha";
    public static final String SERVLET_NO_ACCESS = "/ServletNoAccess";
    public static final String NAME_SERVLET_NO_ACCESS = "ServletNoAccess";
    public static final String MSG_NO_ACCESS = "No tienes acceso a este recurso";
    public static final String MSG_NOT_AUTHORIZED_ADMIN_USERS = "No tienes autorización para registrar usuarios admin";
    public static final String SERVLET_LOGOUT_NAME = "ServletLogout";
    public static final String SERVLET_LOGOUT_PATH = "/basketball/servletlogout";
    public static final String EMPTY_SPACE = " ";
    public static final String BASIC = "Basic";
    public static final String USER_PASS_SPLIT = ":";
    public static final String JWT = "JWT";
    public static final String ALGORITHM = "SHA-512";
    public static final String ADMIN = "ADMIN";
    public static final String NORMAL = "NORMAL";
    public static final String USER = "user";
    public static final String GROUP = "group";
    public static final String TOKEN_EXPIRADO = "Token expirado";
    public static final String TOKEN_SIGNATURE_INCORRECT = "Firma de token incorrecta";
    public static final String LOGUEADO_CORRECTAMENTE = "Logueado correctamente";
    public static final String LOGIN_PATH = "/login";
    public static final String CLAVE = "clave";
    public static final String JWT_SUBJECT = "BasketApp Authentication";
    public static final String JWT_ISSUER = "org.quevedo";


    private EEConst() {
    }
}
