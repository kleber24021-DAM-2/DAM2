package org.quevedo.secretkeeper.dao.security;

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
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSAEncryption";
    public static final String KEY_STORE_ALGORITHM = "PKCS12";
    public static final String PRIVATE = " private";
    public static final String KEY_STORE_PATH = "KeyStore.pfx";
    public static final String MSG_CREATED_CERT = "Se ha creado el keyStore correctamente";
    public static final String MSG_PROBLEM_CERT = "Ha surgido un problema al crear el keyStore";

    private SecurityConsts() {
    }
}
