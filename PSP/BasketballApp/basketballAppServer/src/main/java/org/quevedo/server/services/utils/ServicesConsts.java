package org.quevedo.server.services.utils;

public class ServicesConsts {
    public static final String MSG_MAIL_ALREADY_EXISTS = "El correo introducido ya existe";
    public static final String MSG_PASSWORD_NOT_MATCH = "La confirmación de contraseña no coincide";
    public static final String MSG_NOT_VALID_EMAIL = "El email introducido no es válido";
    public static final String MSG_TIMEOUT_CONFIRMATION = "No ha confirmado el registro en el tiempo establecido.";
    public static final String MSG_REGISTER_AGAIN = " Por favor, vuelva a registrarse";
    public static final String MSG_NOT_DELETED_ACCOUNT_ADMIN = "No se ha podido borrar su cuenta, por favor contácte con el administrador";
    public static final String MSG_ALREADY_ACTIVE_ACCOUNT = "Esta cuenta ya está activa";
    public static final String MSG_USER_ACTIVATED = "El usuario ha sido activado";
    public static final String MSG_DB_NOT_CONNECTED = "No se ha podido conectar con la base de datos, por favor, vuelva a intentarlo";
    public static final String CONFIRMACION_MENSAJE_2 = "\">Aqui</a></html>";
    public static final String MAIL_CONFIRMATION_SUBJECT = "Confirmación";
    public static final String SUBJECT_PASSWORD_CHANGE = "Cambio de contraseña";
    public static final String MSG_PASSWORD_CHANGED = "Contraseña cambiada";
    public static final String MSG_PASSWORD_NOT_CHANGED = "No se ha podido cambiar la contraseña. Por favor, vuelva a intentarlo o vuelva a solicitar el cambio de contraseña";
    public static final String MSG_PASSWORD_LINK_TIMEOUT = "Este enlace de cambio de contraseña ha caducado. Por favor, vuelva a solicitar el cambio de contraseña";
    public static final String MSG_PASSWORD_CONFIRM_MISMATCH = "Por favor, compruebe que las dos contraseñas coinciden.";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String CONFIRMACION_MENSAJE = "<html><h1>Se ha registrado un usuario con este correo</h1><br> Para confirmar pulse en el siguiente enlace <a href=\"http://localhost:8080/basketballAppServer-1.0-SNAPSHOT";
    public static final String MAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String PBKDF_2_PASSWORD_HASH_ITERATIONS = "Pbkdf2PasswordHash.Iterations";
    public static final String PBKDF_2_PASSWORD_HASH_ITERATIONS_VALUE = "3072";
    public static final String PBKDF_2_PASSWORD_HASH_ALGORITHM = "Pbkdf2PasswordHash.Algorithm";
    public static final String PBKDF_2_PASSWORD_HASH_ALGORITHM_VALUE = "PBKDF2WithHmacSHA512";
    public static final String PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES = "Pbkdf2PasswordHash.SaltSizeBytes";
    public static final String PBKDF_2_PASSWORD_HASH_SALT_SIZE_BYTES_VALUE = "32";
    public static final String PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES = "Pbkdf2PasswordHash.KeySizeBytes";
    public static final String PBKDF_2_PASSWORD_HASH_KEY_SIZE_BYTES_VALUE = "32";

    private ServicesConsts() {
    }
}
