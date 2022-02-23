package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.common.models.Usuario;
import org.quevedo.server.dao.interfaces.DaoUsuario;
import org.quevedo.server.security.asimetrical.CertificateUtils;

public class ServiceUsuario {
    private final CertificateUtils certificateUtils;
    private final DaoUsuario daoUsuario;

    @Inject
            public ServiceUsuario(
                    CertificateUtils certificateUtils,
                    DaoUsuario daoUsuario
    ){
        this.certificateUtils = certificateUtils;
        this.daoUsuario = daoUsuario;
    }

    /**
     * @return the certificate of the created user
     */
    public Either<String, Usuario> registerUser(String username, String publicKey) {
        Either<String, Usuario> result;
        Either<String, String> certificateResult = certificateUtils.createNewCert(username, publicKey);
        if (certificateResult.isRight()){
            Either<String, Usuario> userSaveResult = daoUsuario.registerUser(username, certificateResult.get());
            if (userSaveResult.isRight()){
                result = Either.right(userSaveResult.get());
            }else {
                result = Either.left(userSaveResult.getLeft());
            }
        }else {
            result = Either.left(certificateResult.getLeft());
        }
        return result;
    }

    public Either<String, Boolean> checkUserExists(String username){
        return daoUsuario.userExists(username);
    }
}
