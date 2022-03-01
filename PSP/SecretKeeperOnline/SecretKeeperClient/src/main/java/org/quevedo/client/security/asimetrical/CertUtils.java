package org.quevedo.client.security.asimetrical;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.quevedo.client.config.Configuration;
import org.quevedo.common.consts.SecurityConsts;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
public class CertUtils {
    public static final String KEY_STORES = "keyStores\\";
    private final KeyPairGenerator keyGen;
    private final SecureRandom secureRandom;
    private final BouncyCastleProvider bouncyCastleProvider;
    private final Configuration configuration;

    @Inject
    public CertUtils(
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

    public KeyPair createUserKeyPair() {
        configuration.loadConfig();
        Security.addProvider(bouncyCastleProvider);
        return keyGen.generateKeyPair();
    }

    public Either<String, Boolean> createKeyStore(String serverCertificate, KeyPair userKeys, String username, String password) {
        Either<String, Boolean> result;
        try {
            X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(serverCertificate));
            KeyStore keyStore = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            char[] passChar = password.toCharArray();
            keyStore.load(null, null);
            keyStore.setCertificateEntry(username, cert);
            keyStore.setKeyEntry(username + SecurityConsts.PRIVATE, userKeys.getPrivate(), passChar, new Certificate[]{cert});
            FileOutputStream fileOutputStream = new FileOutputStream(KEY_STORES + username + SecurityConsts.KEY_STORE_PATH);
            keyStore.store(fileOutputStream, passChar);
            fileOutputStream.close();
            result = Either.right(true);
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    public Either<String, Boolean> createNewCertEntry(String username, String password) {
        Either<String, Boolean> result;
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
            FileOutputStream fileOutputStream = new FileOutputStream(KEY_STORES + username + SecurityConsts.KEY_STORE_PATH);
            keyStore.store(fileOutputStream, password.toCharArray());
            fileOutputStream.close();

            result = Either.right(true);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | KeyStoreException | IOException | CertificateException e) {
            log.error(e.getMessage(), e);
            result = Either.left(SecurityConsts.MSG_PROBLEM_CERT);
        }
        return result;
    }

    public Either<String, KeyPair> checkUserCertificate(String username, String password) {
        Either<String, KeyPair> result;
        try (FileInputStream fis = new FileInputStream(KEY_STORES + username + SecurityConsts.KEY_STORE_PATH)) {
            configuration.loadConfig();
            KeyStore ksLoad = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            ksLoad.load(fis, configuration.getGeneralPassword().toCharArray());
            X509Certificate certificateLoaded = (X509Certificate) ksLoad.getCertificate(username);
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(username + SecurityConsts.PRIVATE, pt);
            PrivateKey keyLoaded = privateKeyEntry.getPrivateKey();
            result = Either.right(new KeyPair(certificateLoaded.getPublicKey(), keyLoaded));
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableEntryException e) {
            log.error(e.getMessage(), e);
            result = Either.left(SecurityConsts.MSG_PROBLEM_OPEN_CERT);
        }
        return result;
    }

    public Either<String, Tuple2<String, KeyPair>> getUserCertificateAndKeyPair(String username, String password) {
        Either<String, Tuple2<String, KeyPair>> result;
        Tuple2<String, KeyPair> certificateAndKeyPair = new Tuple2<>("", null);
        try (FileInputStream fis = new FileInputStream(KEY_STORES + username + SecurityConsts.KEY_STORE_PATH)) {
            configuration.loadConfig();
            KeyStore ksLoad = KeyStore.getInstance(SecurityConsts.KEY_STORE_ALGORITHM);
            ksLoad.load(fis, password.toCharArray());
            X509Certificate certificateLoaded = (X509Certificate) ksLoad.getCertificate(username);
            certificateAndKeyPair = certificateAndKeyPair.update1(X509CertUtils.toPEMString(certificateLoaded));
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) ksLoad.getEntry(username + SecurityConsts.PRIVATE, pt);
            PrivateKey keyLoaded = privateKeyEntry.getPrivateKey();
            certificateAndKeyPair = certificateAndKeyPair.update2(new KeyPair(certificateLoaded.getPublicKey(), keyLoaded));
            result = Either.right(certificateAndKeyPair);
        } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException | UnrecoverableEntryException exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    public Either<String, String> signMessage(String message, PrivateKey privateKey) {
        Either<String, String> result;
        try {
            Signature sign = Signature.getInstance(SecurityConsts.SIGN_ALGORITHM);
            sign.initSign(privateKey);
            sign.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] firma = sign.sign();
            result = Either.right(Base64.getUrlEncoder().encodeToString(firma));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }

    public Either<String, Boolean> verifySign(String message, String firma, String userCertificateBase64) {
        Either<String, Boolean> result;
        try {
            X509Certificate userCertificate = X509CertUtils.parse(Base64.getUrlDecoder().decode(userCertificateBase64));
            Signature sign = Signature.getInstance(SecurityConsts.SIGN_ALGORITHM);
            sign.initVerify(userCertificate.getPublicKey());
            sign.update(message.getBytes(StandardCharsets.UTF_8));
            result = Either.right(sign.verify(Base64.getUrlDecoder().decode(firma)));
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(exception.getMessage());
        }
        return result;
    }
}
