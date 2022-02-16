package org.quevedo.secretkeeper.dao.security;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.quevedo.secretkeeper.config.Configuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
public class CertCreate {
    public static final String KEY_STORES = "keyStores\\";
    private final KeyPairGenerator keyGen;
    private final SecureRandom secureRandom;
    private final BouncyCastleProvider bouncyCastleProvider;
    private final Configuration configuration;

    @Inject
    public CertCreate(
            @Named(SecurityConsts.KEY_GENERATOR_NAME) KeyPairGenerator keyGen,
            @Named(SecurityConsts.RANDOM) SecureRandom secureRandom,
            BouncyCastleProvider bouncyCastleProvider,
            Configuration configuration
    ) {
        this.bouncyCastleProvider = bouncyCastleProvider;
        this.keyGen = keyGen;
        this.secureRandom = secureRandom;
        this.configuration = configuration;
    }

    public Either<String, String> createNewCertEntry(String username, String password) {
        Either<String, String> result;
        try {
            configuration.loadConfig();
            Security.addProvider(bouncyCastleProvider);
            KeyPair clavesRSA = keyGen.generateKeyPair();
            PrivateKey clavePrivada = clavesRSA.getPrivate();
            PublicKey clavePublica = clavesRSA.getPublic();

            X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
            certGenerator.setSerialNumber(BigInteger.valueOf(Math.abs(secureRandom.nextInt())));
            certGenerator.setSubjectDN(new X509Principal(SecurityConsts.CN + username));
            certGenerator.setIssuerDN(new X509Principal(SecurityConsts.CN_ISSUER));
            certGenerator.setPublicKey(clavePublica);
            certGenerator.setNotBefore(
                    Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC))
            );
            certGenerator.setNotAfter(new Date());
            certGenerator.setSignatureAlgorithm(SecurityConsts.SIGNATURE_ALGORITHM);

            X509Certificate cert = certGenerator.generate(clavePrivada);

            KeyStore keyStore = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            char[] passChar = password.toCharArray();
            keyStore.load(null, null);
            keyStore.setCertificateEntry(username, cert);
            keyStore.setKeyEntry(username + SecurityConsts.PRIVATE, clavePrivada, passChar, new Certificate[]{cert});
            FileOutputStream fileOutputStream = new FileOutputStream(KEY_STORES + username+SecurityConsts.KEY_STORE_PATH);
            keyStore.store(fileOutputStream, configuration.getGeneralPassword().toCharArray());
            fileOutputStream.close();

            result = Either.right(SecurityConsts.MSG_CREATED_CERT);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | KeyStoreException | IOException | CertificateException e) {
            log.error(e.getMessage(), e);
            result = Either.left(SecurityConsts.MSG_PROBLEM_CERT);
        }
        return result;
    }

    public Either<String, String> checkUserCertificate(String username, String password) {
        Either<String, String> result;
        try {
            configuration.loadConfig();
            KeyStore ksLoad = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            ksLoad.load(new FileInputStream(KEY_STORES + username+SecurityConsts.KEY_STORE_PATH), configuration.getGeneralPassword().toCharArray());
            X509Certificate certificateLoaded = (X509Certificate) ksLoad.getCertificate(username);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(username + SecurityConsts.PRIVATE, pt);
            PrivateKey keyLoaded = privateKeyEntry.getPrivateKey();
            result = Either.right(keyLoaded.toString());
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableEntryException e) {
            log.error(e.getMessage(), e);
            result = Either.left(SecurityConsts.MSG_PROBLEM_CERT);
        }
        return result;
    }
}
