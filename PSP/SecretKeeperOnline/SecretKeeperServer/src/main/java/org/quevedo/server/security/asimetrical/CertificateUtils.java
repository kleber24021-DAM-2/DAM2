package org.quevedo.server.security.asimetrical;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.quevedo.common.consts.SecurityConsts;
import org.quevedo.server.config.Configuration;
import org.quevedo.server.ee.EEConsts;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Log4j2
public class CertificateUtils {
    private final Configuration config;

    @Inject
    private CertificateUtils(Configuration config) {
        this.config = config;
    }

    public Either<String, String> createNewCert(String username, String publicKeyAsBase64) {
        Either<String, String> result;
        Security.addProvider(new BouncyCastleProvider());
        try {
            byte[] publicKeyArrays = Base64.getUrlDecoder().decode(publicKeyAsBase64);
            PublicKey userPublicKey = KeyFactory.getInstance(SecurityConsts.KEYPAIR_ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKeyArrays));
            X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
            SecureRandom secureRandom = new SecureRandom();
            certGenerator.setSerialNumber(BigInteger.valueOf(Math.abs(secureRandom.nextInt())));
            certGenerator.setSubjectDN(new X509Principal(SecurityConsts.CN + username));
            certGenerator.setIssuerDN(new X509Principal(SecurityConsts.CN_ISSUER));
            certGenerator.setPublicKey(userPublicKey);
            certGenerator.setNotBefore(
                    Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC))
            );
            certGenerator.setNotAfter(new Date());
            certGenerator.setSignatureAlgorithm(SecurityConsts.SIGNATURE_ALGORITHM);
            X509Certificate cert = certGenerator.generateX509Certificate(config.getServerKeys().getPrivate());
            result = Either.right(Base64.getUrlEncoder().encodeToString(cert.getEncoded()));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | SignatureException | InvalidKeyException | CertificateEncodingException e) {
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }

    public Either<String, Void> checkCert(String certificate) {
        Either<String, Void> result;
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] certBytes = Base64.getUrlDecoder().decode(certificate);
            X509Certificate cert = X509CertUtils.parse(certBytes);
            cert.verify(config.getServerKeys().getPublic());
            if (cert.getIssuerDN().getName().equals(SecurityConsts.CN_ISSUER)) {
                result = Either.right(null);
            } else {
                result = Either.left(EEConsts.ISSUER_INCORRECTO);
            }
        } catch (InvalidKeyException invalidKeySpecException) {
            result = Either.left(EEConsts.FIRMA_INCORRECTA);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = Either.left(e.getMessage());
        }
        return result;
    }

    public String getNameOfCertificate(String certificate) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            byte[] certBytes = Base64.getUrlDecoder().decode(certificate);
            X509Certificate cert = X509CertUtils.parse(certBytes);
            return cert.getSubjectDN().getName();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }
}
