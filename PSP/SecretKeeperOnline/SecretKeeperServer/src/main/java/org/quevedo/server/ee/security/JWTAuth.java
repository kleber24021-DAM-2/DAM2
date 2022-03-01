package org.quevedo.server.ee.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;
import org.quevedo.server.ee.EEConsts;
import org.quevedo.server.security.asimetrical.CertificateUtils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Singleton
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {
    public static final String SPLIT_VALUE = " ";
    private final Key key;
    private final CertificateUtils certificateUtils;
    private final InMemoryIdentityStore inMemoryIdentityStore;

    @Inject
    public JWTAuth(
            @Named(EEConsts.KEY_PROVIDER) Key key,
            CertificateUtils certificateUtils,
            InMemoryIdentityStore inMemoryIdentityStore) {
        this.key = key;
        this.certificateUtils = certificateUtils;
        this.inMemoryIdentityStore = inMemoryIdentityStore;
    }


    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult credentialValidationResult = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] headerValues = header.split(SPLIT_VALUE);
            if (headerValues[0].equalsIgnoreCase(EEConsts.CERT_HEADER)) {
                String certificado = headerValues[1];
                if (!Objects.equals(certificado, EEConsts.NULL)) {
                    String username = certificateUtils.getNameOfCertificate(certificado);
                    if (username != null) {
                        credentialValidationResult = inMemoryIdentityStore
                                .validate(new UsernamePasswordCredential(username, certificado));
                    }
                    if (credentialValidationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                        String token = generateToken(credentialValidationResult);
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, token);
                    }
                }
            } else if (headerValues[0].equalsIgnoreCase(EEConsts.BEARER_HEADER)) {
                try {
                    Jws<Claims> jws = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(headerValues[1]);
                    List<String> roles = (List<String>) jws.getBody().get(EEConsts.GROUPS);
                    credentialValidationResult = new CredentialValidationResult(
                            jws.getBody().get(EEConsts.USER).toString(),
                            new HashSet<>(roles)
                    );
                } catch (ExpiredJwtException expired) {
                    log.error(expired.getMessage(), expired);
                    httpServletResponse.setHeader(HttpHeaders.EXPIRES, EEConsts.EL_TIEMPO_DEL_TOKEN_HA_EXPIRADO);
                    httpServletResponse.setStatus(418);
                } catch (MalformedJwtException modificado) {
                    log.error(modificado.getMessage(), modificado);
                    httpServletResponse.setHeader(EEConsts.ERROR, EEConsts.EL_TOKEN_HA_SIDO_MODIFICADO);
                    httpServletResponse.setStatus(418);
                } catch (SignatureException exception) {
                    log.error(exception.getMessage(), exception);
                    httpServletResponse.setHeader(EEConsts.ERROR, EEConsts.LA_FIRMA_DEL_TOKEN_NO_ES_VALIDA);
                    httpServletResponse.setStatus(418);
                }
            }
        }
        if (credentialValidationResult.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(credentialValidationResult);
    }

    private String generateToken(CredentialValidationResult credentialValidationResult) {

        return Jwts.builder()
                .setExpiration(Date.from(
                        LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault())
                                .toInstant())
                ).claim(EEConsts.USER, credentialValidationResult.getCallerPrincipal().getName())
                .claim(EEConsts.GROUPS, credentialValidationResult.getCallerGroups())
                .signWith(key).compact();
    }
}
