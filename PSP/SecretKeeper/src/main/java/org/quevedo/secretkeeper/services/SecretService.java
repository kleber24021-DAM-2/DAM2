package org.quevedo.secretkeeper.services;

import io.vavr.control.Either;
import org.quevedo.secretkeeper.dao.api.DaoSecrets;
import org.quevedo.secretkeeper.dao.implementations.DaoSecretsSpring;
import org.quevedo.secretkeeper.dao.utils.DaoConsts;
import org.quevedo.secretkeeper.dao.utils.UserCache;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;
import org.quevedo.secretkeeper.model.Secret;
import org.quevedo.secretkeeper.security.asimetrical.CertificateUtils;
import org.quevedo.secretkeeper.security.asimetrical.CipherAssimetrical;
import org.quevedo.secretkeeper.security.simetrical.GCMCipher;
import org.quevedo.secretkeeper.security.utils.RandomPassGenerator;
import org.quevedo.secretkeeper.security.utils.SecurityConsts;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class SecretService {
    public static final String MIDDLE_MSG = "; Usuario: ";
    public static final String OK = "OK";
    private final DaoSecrets daoSecrets;
    private final GCMCipher gcmCipher;
    private final UserCache userCache;
    private final CipherAssimetrical cipherAssimetrical;
    private final RandomPassGenerator randomPassGenerator;
    private final CertificateUtils certUtils;

    @Inject
    public SecretService(
            DaoSecretsSpring daoSecrets,
            GCMCipher gcmCipher,
            UserCache userCache,
            @Named(SecurityConsts.RANDOM_PASS_GENERATOR)
                    RandomPassGenerator randomPassGenerator,
            CipherAssimetrical cipherAssimetrical,
            CertificateUtils certUtils
    ) {
        this.daoSecrets = daoSecrets;
        this.userCache = userCache;
        this.gcmCipher = gcmCipher;
        this.randomPassGenerator = randomPassGenerator;
        this.cipherAssimetrical = cipherAssimetrical;
        this.certUtils = certUtils;
    }

    public Either<String, Secret> saveSecret(Secret toSave) {
        Either<String, Secret> result;
        AtomicReference<Either<String, String>> tempResult = new AtomicReference<>();
        String randomPass = randomPassGenerator.generateSecureRandomPassword();

        Map<String, String> sharedUsers = new HashMap<>();
        toSave.addToSharedUsers(userCache.getUser(), DaoConsts.EMPTY_STRING);

        toSave.getSharedWith()
                .forEach((user, pass) -> certUtils.getUserPublicKey(user)
                        .peek(publicKey -> cipherAssimetrical.cipherAsimetrically(randomPass, publicKey)
                                .peek(encriptedPass -> {
                                    sharedUsers.put(user, encriptedPass);
                                    tempResult.set(Either.right(OK));
                                })
                                .peekLeft(s -> tempResult.set(Either.left(s + MIDDLE_MSG + user))))
                        .peekLeft(error -> tempResult.set(Either.left(error + MIDDLE_MSG + user))));

        String encryptedMessage = gcmCipher.encrypt(toSave.getMessage(), randomPass);

        if (tempResult.get().isRight()) {
            toSave.setMessage(encryptedMessage);
            toSave.setSharedWith(sharedUsers);
            result = daoSecrets.saveSecret(toSave);
        } else {
            result = Either.left(tempResult.get().getLeft());
        }
        return result;
    }

    public Either<String, Secret> fetchSecret(Secret toFetch) {
        Either<String, Secret> result;
        Either<String, Secret> fetchResult = daoSecrets.fetchSecret(toFetch);
        if (fetchResult.isRight()) {
            Secret fetchedSecret = fetchResult.get();
            String getUserFromSecret = fetchedSecret.getSharedWith().get(userCache.getUser());
            if (getUserFromSecret != null) {
                Either<String, String> uncipherResult = cipherAssimetrical.uncipherAsimetrically(getUserFromSecret, userCache.getKeyPair().getPrivate());
                if (uncipherResult.isRight()) {
                    toFetch.setMessage(gcmCipher.decrypt(fetchedSecret.getMessage(), uncipherResult.get()));
                    result = Either.right(toFetch);
                } else {
                    result = Either.left(uncipherResult.getLeft());
                }
            }else {
                result = Either.left(GuiConsts.MSG_NOT_SECRET_ACCESS);
            }
        } else {
            result = Either.left(GuiConsts.MSG_DB_ERROR);
        }
        return result;
    }

    public Either<String, List<Secret>> getAllSecrets() {
        return daoSecrets.getAllUndecryptedSecrets();
    }

    public void closePool() {
        daoSecrets.closePool();
    }
}
