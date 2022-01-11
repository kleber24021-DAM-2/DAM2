package org.quevedo.server.services.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomCode {
    private RandomCode() {
    }

    public static String generateRandomBytes() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }
}
