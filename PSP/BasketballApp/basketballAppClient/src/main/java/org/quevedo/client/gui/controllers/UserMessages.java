package org.quevedo.client.gui.controllers;

public class UserMessages {
    public static final String MSG_DB_NO_CONNECTION = "No se ha podido conectar a la base de datos";
    public static final String MSG_UNEXPECTED_ERROR = "Ha surgido un error inesperado. Vuelva a intentarlo";
    public static final String MSG_NO_EMPTY_FIELD = "Debes de rellenar todos los campos";
    public static final String MSG_NUM_EX = "Los resultados deben de ser números. Se guardará el partido con resultado vacío";
    public static final String MSG_EMAIL_SENDED = "Se le ha enviado un correo de confirmación";
    public static final String MSG_PARTIDO_RESULT_INFO = "Debe de seleccionar un partido y rellenar los campos de resultado para poder actualizarlo";
    public static final String MSG_VALID_MAIL = "Por favor introduzca un correo válido";
    public static final String MAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String MSG_INTRODUCE_PASS = "Por favor, introduzca una contraseña";
    public static final String MSG_NOT_MATCHING_PASS = "Las contraseñas no coinciden";
    public static final String MSG_INFO_EMAIL = "Se le ha enviado un correo de confirmación";
    public static final String MSG_INSERT_MAIL = "Debe de introducir un correo para iniciar el cambio de contraseña";

    private UserMessages() {
    }
}
