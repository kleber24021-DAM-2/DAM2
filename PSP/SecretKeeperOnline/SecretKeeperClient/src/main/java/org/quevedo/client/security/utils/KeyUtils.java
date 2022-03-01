package org.quevedo.client.security.utils;

import org.quevedo.common.consts.SecurityConsts;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class KeyUtils {
    public String passKeyToBase64(Key key) {
        return Base64.getUrlEncoder().encodeToString(key.getEncoded());
    }

    public Key passBase64ToKey(String key) {
        return new SecretKeySpec(Base64.getUrlDecoder().decode(key), SecurityConsts.KEYPAIR_ALGORITHM);
    }
}
