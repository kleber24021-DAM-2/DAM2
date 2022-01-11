package org.quevedo.server.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.quevedo.server.dao.implementations.DaoUsuarios;
import org.quevedo.server.dao.model.Usuario;
import org.quevedo.server.services.utils.RandomCode;
import org.quevedo.server.services.utils.ServicesConsts;
import org.quevedo.sharedmodels.servererror.ServerError;
import org.quevedo.sharedmodels.servererror.TipoError;
import org.quevedo.sharedmodels.usuario.TipoUsuario;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ServiceUsuarios {
    private final DaoUsuarios daoUsuarios;
    private final ServiceHashPassword serviceHashPassword;
    private final ServiceMandarMail serviceMandarMail;

    @Inject
    public ServiceUsuarios(DaoUsuarios daoUsuarios, ServiceHashPassword serviceHashPassword, ServiceMandarMail serviceMandarMail) {
        this.daoUsuarios = daoUsuarios;
        this.serviceHashPassword = serviceHashPassword;
        this.serviceMandarMail = serviceMandarMail;
    }

    public Either<String, List<UsuarioGetDTO>> getAllUsuarios() {
        return daoUsuarios.getAllUsuarios();
    }

    public Either<ServerError, Usuario> registerDTOToUsuario(UsuarioRegisterDTO newDtoUser) {
        Either<ServerError, Usuario> conversionResult;
        if (daoUsuarios.checkUserMail(newDtoUser.getCorreo()) != 0) {
            conversionResult = Either.left(new ServerError(ServicesConsts.MSG_MAIL_ALREADY_EXISTS, TipoError.BAD_INPUT));
        } else if (!newDtoUser.getPassword().equals(newDtoUser.getPasswordConfirmation())) {
            conversionResult = Either.left(new ServerError(ServicesConsts.MSG_PASSWORD_NOT_MATCH, TipoError.BAD_INPUT));
        } else if (!checkEmailIsValid(newDtoUser.getCorreo())) {
            conversionResult = Either.left(new ServerError(ServicesConsts.MSG_NOT_VALID_EMAIL, TipoError.BAD_INPUT));
        } else {
            Usuario newUsuario = new Usuario();
            newUsuario.setCorreo(newDtoUser.getCorreo());
            newUsuario.setPassword(serviceHashPassword.hash(newDtoUser.getPassword()));
            newUsuario.setTipoUsuario(newDtoUser.getTipoUsuario());
            newUsuario.setActivo(false);
            newUsuario.setCodigoActivacion(RandomCode.generateRandomBytes());
            newUsuario.setFechaActivacion(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
            conversionResult = Either.right(newUsuario);
        }
        return conversionResult;
    }

    public Either<ServerError, UsuarioGetDTO> registerUsuario(Usuario newUser) {
        return daoUsuarios.crearUsuario(newUser);
    }

    public String activateUsuario(String codActivacion) {
        Usuario toActivateUser;
        String toReturn;
        Either<String, Usuario> queryResult = daoUsuarios.getUsuarioByCodActivacion(codActivacion);
        if (queryResult.isRight()) {
            toActivateUser = queryResult.get();
            if (toActivateUser.getFechaActivacion().isBefore(LocalDateTime.now())) {
                toReturn = ServicesConsts.MSG_TIMEOUT_CONFIRMATION;
                if (daoUsuarios.deleteUsuario(toActivateUser)) {
                    toReturn += ServicesConsts.MSG_REGISTER_AGAIN;
                } else {
                    toReturn += ServicesConsts.MSG_NOT_DELETED_ACCOUNT_ADMIN;
                }
            } else if (toActivateUser.isActivo()) {
                toReturn = ServicesConsts.MSG_ALREADY_ACTIVE_ACCOUNT;
            } else {
                if (daoUsuarios.activateUsuario(toActivateUser)) {
                    toReturn = ServicesConsts.MSG_USER_ACTIVATED;
                } else {
                    toReturn = ServicesConsts.MSG_DB_NOT_CONNECTED;
                }
            }
        } else {
            toReturn = queryResult.getLeft();
        }
        return toReturn;
    }

    private boolean checkEmailExists(String toCheckEmail) {
        return checkEmailIsValid(toCheckEmail) && daoUsuarios.checkUserMail(toCheckEmail) == 1;
    }

    public Either<String, Usuario> getUserByRandomCode(String randomCode) {
        return daoUsuarios.getUsuarioByCodActivacion(randomCode);
    }

    private boolean checkEmailIsValid(String email) {
        return email.matches(ServicesConsts.MAIL_REGEX);
    }

    public String confirmPasswordChange(String codCambioPass, String newPassword, String newPasswordConfirmation) {
        Either<String, Usuario> actualUser = getUserByRandomCode(codCambioPass);
        String toReturn;
        if (actualUser.isRight()) {
            if (newPassword.equals(newPasswordConfirmation)) {
                if (actualUser.get().getFechaActivacion().isAfter(LocalDateTime.now())) {
                    String hashedPassword = serviceHashPassword.hash(newPassword);
                    if (daoUsuarios.changePassword(actualUser.get(), hashedPassword)) {
                        toReturn = ServicesConsts.MSG_PASSWORD_CHANGED;
                    } else {
                        toReturn = ServicesConsts.MSG_PASSWORD_NOT_CHANGED;
                    }
                } else {
                    toReturn = ServicesConsts.MSG_PASSWORD_LINK_TIMEOUT;
                }
            } else {
                toReturn = ServicesConsts.MSG_PASSWORD_CONFIRM_MISMATCH;
            }
        } else {
            toReturn = actualUser.getLeft();
        }
        return toReturn;
    }

    public void startPasswordChange(String changePasswordEmail) {
        if (checkEmailExists(changePasswordEmail)) {
            String newRandomCode = RandomCode.generateRandomBytes();
            if (daoUsuarios.startPasswordChange(newRandomCode, changePasswordEmail, LocalDateTime.now().plus(1, ChronoUnit.DAYS))) {
                serviceMandarMail.sendChangePasswordMail(changePasswordEmail, newRandomCode);
            }
        }
    }

    public Either<String, TipoUsuario> checkLogin(String correo, String pass) {
        Either<String, TipoUsuario> result;
        Either<String, Usuario> queryResult = daoUsuarios.getUsuarioByCorreo(correo);
        if (queryResult.isRight()) {
            if (queryResult.get() != null && queryResult.get().isActivo() && serviceHashPassword.verify(queryResult.get().getPassword(), pass)) {
                result = Either.right(queryResult.get().getTipoUsuario());
            } else {
                result = Either.right(null);
            }
        } else {
            result = Either.left(queryResult.getLeft());
        }
        return result;
    }

    public Either<ServerError, TipoUsuario> getUserLevelByMail(String email) {
        return daoUsuarios.getUserLevelByMail(email);
    }
}
