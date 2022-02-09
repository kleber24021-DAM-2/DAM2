package org.quevedo.secretkeeper.dao.security;

public class SecurityConsts {
    public static final String CIPHER_ALGORITHM = "AES/GCM/noPadding";
    public static final String KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final int PARAMETER_SPEC_LENGTH = 128;
    public static final int ITERATION_COUNT = 65536;
    public static final int KEY_LENGTH = 256;
    public static final String SECRET_KEY_ALGORITHM = "AES";
    public static final String CIPHER_NAME = "Cipher";

    private SecurityConsts() {
    }
}
