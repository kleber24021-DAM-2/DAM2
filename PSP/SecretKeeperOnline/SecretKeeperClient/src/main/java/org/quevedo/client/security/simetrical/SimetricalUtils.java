package org.quevedo.client.security.simetrical;

import com.google.common.primitives.Bytes;
import lombok.extern.log4j.Log4j2;
import org.quevedo.common.consts.SecurityConsts;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public class SimetricalUtils {
    private final SecretKeyFactory factory;
    private final Cipher cipher;

    @Inject
    public SimetricalUtils(SecretKeyFactory factory, @Named(SecurityConsts.CIPHER) Cipher cipher) {
        this.factory = factory;
        this.cipher = cipher;
    }

    public String encrypt(String toEncrypt, String secretPass) {
        String result = null;
        try {
            byte[] iv = new byte[12];
            byte[] salt = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);
            secureRandom.nextBytes(salt);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(SecurityConsts.PARAMETER_SPEC_LENGTH, iv);
            KeySpec spec = new PBEKeySpec(secretPass.toCharArray(), salt, SecurityConsts.ITERATION_COUNT, SecurityConsts.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), SecurityConsts.SECRET_KEY_ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

            result = Base64.getUrlEncoder().encodeToString(Bytes.concat(
                    iv, salt, cipher.doFinal(toEncrypt.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception exception) {
            log.error(exception);
        }
        return result;
    }

    public String decrypt(String strToDecrypt, String password) {
        String result = null;
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(strToDecrypt);
            byte[] iv = Arrays.copyOf(decoded, 12);
            byte[] salt = Arrays.copyOfRange(decoded, 12, 28);

            GCMParameterSpec parameterSpec = new GCMParameterSpec(SecurityConsts.PARAMETER_SPEC_LENGTH, iv);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, SecurityConsts.ITERATION_COUNT, SecurityConsts.KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), SecurityConsts.SECRET_KEY_ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
            result = new String(cipher.doFinal(Arrays.copyOfRange(decoded, 28, decoded.length)));
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
        return result;
    }
}
