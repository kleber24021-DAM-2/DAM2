package org.quevedo.client.gui.utils;

public class GuiConsts {
    public static final String MSG_SELECT_PASSWORD = "Seleccione un mensaje para descrifra e introduzca una contraseña";
    public static final String MSG_INSERT_ENCRYPT = "Introduzca un mensaje, un dueño y una contraseña para cifrar";
    public static final String MAIN_SCREEN = "/fxml/mainScreen.fxml";
    public static final String STYLE = "/css/dark-theme.css";
    public static final String SCREEN_NAME = "Secret Keeper";
    public static final String LOGIN_SCREEN = "/fxml/loginScreen.fxml";
    public static final String MSG_USER_LOGGED = "El usuario se ha logueado correctamente";
    public static final String MSG_USER_NOT_LOGGED = "El usuario y/o contraseña son incorrectos";
    public static final String MSG_USER_REGISTERED = "El usuario ha sido registrado";
    public static final String MSG_USER_NOT_REGISTERED = "El usuario no ha sido registrado. Pruebe otro nombre";
    public static final String MSG_ERROR_READING_USERS = "No se ha podido leer la carpeta";
    public static final String MSG_ERROR_CIPHER = "No se ha podido cifrar. Contacte con el administrador";
    public static final String MSG_NOT_SECRET_ACCESS = "No tienes acceso a este secreto";
    public static final String MSG_DB_ERROR = "Ha habido un error de conexión a la BBDD";
    public static final String MSG_ERROR_UNCIPHER = "No ha sido posible descifrar el mensaje, contacte con el administrador";

    private GuiConsts() {
    }
}
