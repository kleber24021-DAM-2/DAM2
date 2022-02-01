package org.quevedo.client.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.quevedo.client.dao.implementations.DaoUsuarios;
import org.quevedo.sharedmodels.usuario.LoginResultDTO;
import org.quevedo.sharedmodels.usuario.UsuarioGetDTO;
import org.quevedo.sharedmodels.usuario.UsuarioRegisterDTO;

import javax.inject.Inject;
import java.util.List;

public class ServicesUsuarios {
    private final DaoUsuarios daoUsuarios;

    @Inject
    public ServicesUsuarios(DaoUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public Single<Either<String, List<UsuarioGetDTO>>> getAllUsers() {
        return daoUsuarios.getAllUsers().observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, UsuarioGetDTO>> registrarUsuarioAdmin(UsuarioRegisterDTO usuarioRegisterDTO) {
        return daoUsuarios.registrarUsuarioAdmin(usuarioRegisterDTO).observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, UsuarioGetDTO>> registrarUsuarioNormal(UsuarioRegisterDTO usuarioRegisterDTO) {
        return daoUsuarios.registrarUsuarioNormal(usuarioRegisterDTO).observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, Void>> changePassword(String changePasswordEmail) {
        return daoUsuarios.changePassword(changePasswordEmail).observeOn(JavaFxScheduler.platform());
    }

    public Single<Either<String, LoginResultDTO>> doLogin(String correo, String password) {
        return daoUsuarios.doLogin(correo, password).observeOn(JavaFxScheduler.platform());
    }
}
