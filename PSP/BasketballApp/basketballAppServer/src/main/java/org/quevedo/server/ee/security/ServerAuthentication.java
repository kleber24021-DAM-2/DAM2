package org.quevedo.server.ee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
import org.quevedo.server.ee.utils.EEConst;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Log4j2
@ApplicationScoped
public class ServerAuthentication implements HttpAuthenticationMechanism {
    private final InMemoryIdentityStore identityStore;
    private final JWTUtils jwtUtils;

    @Inject
    public ServerAuthentication(InMemoryIdentityStore identityStore, JWTUtils jwtUtils) {
        this.identityStore = identityStore;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult validationResult = INVALID_RESULT;
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] headerValues = header.split(EEConst.EMPTY_SPACE);
            if (headerValues[0].equalsIgnoreCase(EEConst.BASIC)) {
                //Proceso de autenticación básico
                String userPass = new String(Base64.getUrlDecoder().decode(headerValues[1]));
                String[] splitUserPass = userPass.split(EEConst.USER_PASS_SPLIT);
                validationResult = identityStore.validate(new UsernamePasswordCredential(
                        splitUserPass[0], splitUserPass[1]));
                if (validationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                    try {
                        String newToken = jwtUtils.getOK2(splitUserPass[0], validationResult.getCallerGroups().stream().findFirst().get());
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, newToken);
                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
                    }
                }
            } else {
                //Beat it (beat it), beat it (beat it) no one wants to be defeated
                String token = headerValues[1];
                try {
                    Jws<Claims> tokenClaims = jwtUtils.verifyToken(token);
                    String username = tokenClaims.getBody().get(EEConst.USER).toString();
                    String rol = tokenClaims.getBody().get(EEConst.GROUP).toString();
                    validationResult = new CredentialValidationResult(username, Collections.singleton(rol));
                    String newToken = jwtUtils.getOK2(username, rol);
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, newToken);
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    log.error(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
                } catch (ExpiredJwtException expiredJwtException){
                    try{
                        httpServletResponse.sendError(418, EEConst.TOKEN_EXPIRADO);
                    }catch (IOException ioEx){
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, EEConst.TOKEN_EXPIRADO);
                    }
                }catch (SignatureException signatureException){
                    try{
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, EEConst.TOKEN_SIGNATURE_INCORRECT);
                    }catch (IOException ioEx){
                        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, EEConst.TOKEN_SIGNATURE_INCORRECT);
                    }
                }
            }
        }
        if (validationResult.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(validationResult);
    }
}
