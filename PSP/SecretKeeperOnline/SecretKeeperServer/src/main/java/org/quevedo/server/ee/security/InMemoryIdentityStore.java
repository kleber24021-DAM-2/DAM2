package org.quevedo.server.ee.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.quevedo.server.ee.EEConsts;
import org.quevedo.server.security.asimetrical.CertificateUtils;

import java.util.Collections;

@Singleton
public class InMemoryIdentityStore implements IdentityStore {

    private final CertificateUtils certificateUtils;

    @Inject
    public InMemoryIdentityStore(CertificateUtils certificateUtils) {
        this.certificateUtils = certificateUtils;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = CredentialValidationResult.INVALID_RESULT;

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            Either<String, Void> resultVerification = certificateUtils.checkCert(user.getPasswordAsString());
            if (resultVerification.isRight()) {
                credentialValidationResult = new CredentialValidationResult(user.getCaller(), Collections.singleton(EEConsts.USER));
            }
        }
        return credentialValidationResult;
    }
}
