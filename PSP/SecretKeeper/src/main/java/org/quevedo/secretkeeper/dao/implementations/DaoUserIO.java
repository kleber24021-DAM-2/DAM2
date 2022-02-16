package org.quevedo.secretkeeper.dao.implementations;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.quevedo.secretkeeper.dao.api.DaoUser;
import org.quevedo.secretkeeper.dao.utils.UserCache;
import org.quevedo.secretkeeper.gui.utils.GuiConsts;
import org.quevedo.secretkeeper.security.asimetrical.CertificateUtils;
import org.quevedo.secretkeeper.security.utils.SecurityConsts;

import javax.inject.Inject;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log4j2
public class DaoUserIO implements DaoUser {

    private final CertificateUtils certificateUtils;
    private final UserCache userCache;

    @Inject
    public DaoUserIO(
            CertificateUtils certificateUtils,
            UserCache userCache) {
        this.certificateUtils = certificateUtils;
        this.userCache = userCache;
    }

    @Override
    public Either<String, Boolean> loginUser(String username, String password) {
        AtomicReference<Either<String, Boolean>> result = new AtomicReference<>();
        certificateUtils.checkUserCertificate(username, password)
                .peek(keyPair -> {
                    userCache.setNewUser(username, keyPair);
                    result.set(Either.right(true));
                })
                .peekLeft(error -> result.set(Either.left(error)));
        return result.get();
    }

    @Override
    public Either<String, Boolean> registerUser(String username, String password) {
        return certificateUtils.createNewCertEntry(username, password);
    }

    @Override
    public Either<String, List<String>> getAllUsers() {
        Either<String, List<String>> result;
        try {
            File folder = new File(SecurityConsts.FOLDER_PATH);
            List<File> fileList = List.of(Objects.requireNonNull(folder.listFiles()));
            result = Either.right(fileList.stream()
                    .map(File::getName)
                    .map(name -> name.replace(SecurityConsts.KEY_STORE_PATH, ""))
                    .collect(Collectors.toList()));
        } catch (NullPointerException nullPointerException) {
            result = Either.right(Collections.emptyList());
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            result = Either.left(GuiConsts.MSG_ERROR_READING_USERS);
        }
        return result;
    }
}
