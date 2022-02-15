package org.quevedo.secretkeeper.dao.security;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;


import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
public class CertCreate {
    private final KeyPairGenerator keyGen;
    private final SecureRandom secureRandom;
    private final BouncyCastleProvider bouncyCastleProvider;

    @Inject
    public CertCreate(
            @Named(SecurityConsts.KEY_GENERATOR_NAME) KeyPairGenerator keyGen,
            SecureRandom secureRandom,
            BouncyCastleProvider bouncyCastleProvider
    ){
        this.bouncyCastleProvider = bouncyCastleProvider;
        this.keyGen = keyGen;
        this.secureRandom = secureRandom;
    }

    public Either<String, String> createNewCertEntry(String username, String password){
        try {
            Security.addProvider(bouncyCastleProvider);
            KeyPair clavesRSA = keyGen.generateKeyPair();
            PrivateKey clavePrivada = clavesRSA.getPrivate();
            PublicKey clavePublica = clavesRSA.getPublic();

            X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
            certGenerator.setSerialNumber(BigInteger.valueOf(secureRandom.nextInt()));
            certGenerator.setSubjectDN(new X509Principal("CN=" + username));
            certGenerator.setIssuerDN(new X509Principal("CN=Misco"));
            certGenerator.setPublicKey(clavePublica);
            certGenerator.setNotBefore(
                    Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC))
            );
            certGenerator.setNotAfter(new Date());
            certGenerator.setSignatureAlgorithm("SHA1WithRSAEncryption");

            X509Certificate cert = certGenerator.generate(clavePrivada);


        }catch (CertificateEncodingException | NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            log.error(e.getMessage(), e);
        }
    }
}
