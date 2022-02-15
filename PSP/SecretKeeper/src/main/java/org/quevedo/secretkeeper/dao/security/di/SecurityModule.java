package org.quevedo.secretkeeper.dao.security.di;

import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.quevedo.secretkeeper.dao.security.SecurityConsts;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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

    @Produces
    @Named(SecurityConsts.KEY_GENERATOR_NAME)
    public KeyPairGenerator produceKeyPairGen(){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
        }catch (NoSuchAlgorithmException noSuchAlgorithmException){
            log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        }
        return keyPairGenerator;
    }

    @Produces
    public BouncyCastleProvider bouncyCastleProvider(){
        return new BouncyCastleProvider();
    }

//    @Produces
//    public SecureRandom producerSecureRandom(){
//        return new SecureRandom();
//    }

}
