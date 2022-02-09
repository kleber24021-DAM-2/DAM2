package org.quevedo.secretkeeper.dao.security.di;

import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.dao.security.SecurityConsts;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class SecurityModule {

    @Produces
    public SecretKeyFactory produceSecretKey() {
        SecretKeyFactory secretKeyFactory = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(SecurityConsts.KEY_FACTORY_ALGORITHM);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        }
        return secretKeyFactory;
    }

    @Produces
    @Named(SecurityConsts.CIPHER_NAME)
    public Cipher cipherProducer() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(SecurityConsts.CIPHER_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException cipherException) {
            log.error(cipherException.getMessage(), cipherException);
        }
        return cipher;
    }
}
