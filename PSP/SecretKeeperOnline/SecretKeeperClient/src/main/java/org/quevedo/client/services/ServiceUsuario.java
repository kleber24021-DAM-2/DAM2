package org.quevedo.client.services;

import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.quevedo.client.dao.interfaces.DaoUsuario;
import org.quevedo.client.gui.utils.GuiConsts;
import org.quevedo.client.security.asimetrical.CertUtils;
import org.quevedo.client.security.utils.KeyUtils;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;
import java.security.KeyPair;
import java.util.List;

public class ServiceUsuario {

    private final DaoUsuario daoUsuario;
    private final CertUtils certificateUtils;
    private final KeyUtils keyUtils;

    @Inject
    public ServiceUsuario(
            DaoUsuario daoUsuario,
            CertUtils certificateUtils,
            KeyUtils keyUtils) {
        this.daoUsuario = daoUsuario;
        this.certificateUtils = certificateUtils;
        this.keyUtils = keyUtils;
    }


    public Either<String, Usuario> createUser(String username, String password) {
        Either<String, Usuario> result;
        Either<String, Boolean> nameCheckResult = daoUsuario.userExists(username);
        if (nameCheckResult.isRight()) {
            if (Boolean.FALSE.equals(nameCheckResult.get())) {
                KeyPair keyPair = certificateUtils.createUserKeyPair();
                Either<String, Usuario> resultOfCreation = daoUsuario.registerUser(username, keyUtils.passKeyToBase64(keyPair.getPublic()));
                if (resultOfCreation.isRight()) {
                    Either<String, Boolean> resultOfKeyStoreCreation = certificateUtils.createKeyStore(resultOfCreation.get().getPublicKey(), keyPair, username, password);
                    if (resultOfKeyStoreCreation.isRight()) {
                        result = Either.right(resultOfCreation.get());
                    } else {
                        result = Either.left(resultOfKeyStoreCreation.getLeft());
                    }
                } else {
                    result = Either.left(resultOfCreation.getLeft());
                }
            } else {
                result = Either.left(GuiConsts.MSG_USER_EXISTS);
            }
        } else {
            result = Either.left(nameCheckResult.getLeft());
        }
        return result;
    }

    public Either<String, Usuario> doLogin(String username, String password) {
        Either<String, Usuario> doLogin;
        //1.Cogemos el certificado guardado en local
        Either<String, Tuple2<String, KeyPair>> certificateResult = certificateUtils.getUserCertificateAndKeyPair(username, password);
        if (certificateResult.isRight()) {
            //2.Enviamos el certificado al servidor
            doLogin = daoUsuario.loginUser(username, certificateResult.get());
        } else {
            doLogin = Either.left(certificateResult.getLeft());
        }
        return doLogin;
    }

    public Either<String, List<Usuario>> getAllUsuarios() {
        return daoUsuario.getAllUsers();
    }
}
