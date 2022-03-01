package org.quevedo.client.services;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.client.dao.interfaces.DaoSecretos;
import org.quevedo.client.dao.interfaces.DaoUsuario;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.client.gui.utils.GuiConsts;
import org.quevedo.client.security.asimetrical.AsimetricalUtils;
import org.quevedo.client.security.asimetrical.CertUtils;
import org.quevedo.client.security.simetrical.SimetricalUtils;
import org.quevedo.client.security.utils.RandomPassGenerator;
import org.quevedo.common.consts.SecurityConsts;
import org.quevedo.common.models.Secreto;
import org.quevedo.common.models.SecretoDTO;
import org.quevedo.common.models.UserPassword;
import org.quevedo.common.models.Usuario;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ServiceSecretos {
    private final DaoSecretos daoSecretos;
    private final DaoUsuario daoUsuario;
    private final CacheAuth cacheAuth;
    private final CertUtils certUtils;
    private final SimetricalUtils simetricalUtils;
    private final AsimetricalUtils asimetricalUtils;
    private final RandomPassGenerator randomPassGenerator;

    @Inject
    public ServiceSecretos(DaoSecretos daoSecretos,
                           DaoUsuario daoUsuario,
                           CacheAuth cacheAuth,
                           CertUtils certUtils,
                           SimetricalUtils simetricalUtils,
                           @Named(SecurityConsts.RANDOM_PASS_GENERATOR)
                                   RandomPassGenerator randomPassGenerator,
                           AsimetricalUtils asimetricalUtils) {
        this.daoSecretos = daoSecretos;
        this.daoUsuario = daoUsuario;
        this.cacheAuth = cacheAuth;
        this.certUtils = certUtils;
        this.simetricalUtils = simetricalUtils;
        this.randomPassGenerator = randomPassGenerator;
        this.asimetricalUtils = asimetricalUtils;
    }

    public Either<String, SecretoDTO> saveSecreto(Secreto toSave) {
        Either<String, SecretoDTO> result;
        Secreto refinedToSave = cipherSecret(toSave);
        Either<String, String> signResult = certUtils.signMessage(refinedToSave.getMessage(), cacheAuth.getLoggedKeyPair().getPrivate());
        if (signResult.isRight()) {
            refinedToSave.setSignature(signResult.get());
            result = daoSecretos.saveSecret(refinedToSave);
        } else {
            result = Either.left(signResult.getLeft());
        }
        return result;
    }

    private Secreto cipherSecret(Secreto toCipher) {
        String randomPassword = randomPassGenerator.generateSecureRandomPassword();
        String cipheredMessage = simetricalUtils.encrypt(toCipher.getMessage(), randomPassword);
        toCipher.setSecretOwner(cacheAuth.getLoggedUsername());
        toCipher.setMessage(cipheredMessage);
        Map<Usuario, String> toCipherUserMap = new HashMap<>();

        toCipher.getSharedWith()
                .forEach((usuario, s) -> {
                    Either<String, String> cipheredPassword = asimetricalUtils.cipherAsimetrically(randomPassword, usuario.getPublicKey());
                    if (cipheredPassword.isRight()) {
                        toCipherUserMap.put(usuario, cipheredPassword.get());
                    } else {
                        log.error(cipheredPassword.getLeft());
                    }
                });
        toCipher.setSharedWith(toCipherUserMap);
        return toCipher;
    }

    public Either<String, List<Secreto>> getAllSecretos() {
        return daoSecretos.getAllSecretsByLoggedUser();
    }

    public Either<String, Secreto> decryptSecret(Secreto toDecrypt) {
        Either<String, Secreto> result;
        Either<String, Usuario> getUserResult = daoUsuario.getUserByUsername(toDecrypt.getSecretOwner());
        if (getUserResult.isRight()) {
            //1. Comprobamos la firma del secreto
            Either<String, Boolean> signatureResult = certUtils.verifySign(toDecrypt.getMessage(), toDecrypt.getSignature(), getUserResult.get().getPublicKey());
            if (signatureResult.isRight()) {
                if (Boolean.TRUE.equals(signatureResult.get())) {
                    //2. Desciframos la contraseña cifrada, con la clave privada del usuario logueado
                    Either<String, UserPassword> getPasswordResult = daoSecretos.getPasswordBySecretUserId(toDecrypt.getId(), cacheAuth.getLoggedUserId());
                    if (getPasswordResult.isRight()) {
                        Either<String, String> decipherPasswordResult = asimetricalUtils.uncipherAsimetrically(getPasswordResult.get().getUserPassword(), cacheAuth.getLoggedKeyPair().getPrivate());
                        if (decipherPasswordResult.isRight()) {
                            //3. Desciframos el mensaje con la contraseña resultante
                            String decipherMessageResult = simetricalUtils.decrypt(toDecrypt.getMessage(), decipherPasswordResult.get());
                            if (decipherMessageResult != null) {
                                toDecrypt.setMessage(decipherMessageResult);
                                result = Either.right(toDecrypt);
                            } else {
                                result = Either.left(GuiConsts.MSG_ERROR_DESCIFRANDO_MENSAJE);
                            }
                        } else {
                            result = Either.left(decipherPasswordResult.getLeft());
                        }
                    } else {
                        result = Either.left(getPasswordResult.getLeft());
                    }
                } else {
                    result = Either.left(GuiConsts.MSG_SIGNATURE_ERROR);
                }
            } else {
                result = Either.left(signatureResult.getLeft());
            }
        } else {
            result = Either.left(getUserResult.getLeft());
        }
        return result;
    }
}
