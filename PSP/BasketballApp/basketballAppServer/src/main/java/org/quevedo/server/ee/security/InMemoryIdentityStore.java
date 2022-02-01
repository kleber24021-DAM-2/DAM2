package org.quevedo.server.ee.security;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lombok.NoArgsConstructor;
import org.quevedo.server.services.ServiceUsuarios;
import org.quevedo.sharedmodels.usuario.TipoUsuario;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


@ApplicationScoped
@NoArgsConstructor
public class InMemoryIdentityStore implements IdentityStore {

    private ServiceUsuarios serviceUsuarios;

    @Inject
    public InMemoryIdentityStore(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result = INVALID_RESULT;
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            Either<String, TipoUsuario> tipoUsuarios = serviceUsuarios.checkLogin(user.getCaller(), user.getPasswordAsString());

            if (tipoUsuarios.isRight() && tipoUsuarios.get() != null) {
                result = new CredentialValidationResult(user.getCaller(), Collections.singleton(tipoUsuarios.get().toString()));
            }
        }
        return result;
    }

    @Override
    public int priority() {
        return 10;
    }
}
