package org.quevedo.common.consts;

public class SecurityConsts {
    public static final String CIPHER_ALGORITHM = "AES/GCM/noPadding";
    public static final String KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final int PARAMETER_SPEC_LENGTH = 128;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;
    public static final String SECRET_KEY_ALGORITHM = "AES";
    public static final String CIPHER_NAME = "Cipher";
    public static final String KEY_GENERATOR_NAME = "KeyGenerator";
    public static final String RANDOM = "random";
    public static final String CN = "CN=";
    public static final String CN_ISSUER = "CN=Misco";
    public static final String SIGNATURE_ALGORITHM = "SHA1WITHRSAENCRYPTION";
    public static final String KEY_STORE_ALGORITHM = "PKCS12";
    public static final String PRIVATE = " private";
    public static final String KEY_STORE_PATH = "KeyStore.pfx";
    public static final String MSG_PROBLEM_CERT = "Ha surgido un problema al crear el keyStore";
    public static final String RANDOM_PASS_GENERATOR = "RandomPassGenerator";
    public static final String ASIM_CIPHER_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    public static final String KEYPAIR_ALGORITHM = "RSA";
    public static final String MSG_PROBLEM_OPEN_CERT = "No se ha podido obtener el certificado. Posiblemente la contraseña sea invalida";
    public static final String SIGN_ALGORITHM = "SHA256WithRSA";
    public static final String CIPHER = "Cipher";

    private SecurityConsts() {
    }
}
