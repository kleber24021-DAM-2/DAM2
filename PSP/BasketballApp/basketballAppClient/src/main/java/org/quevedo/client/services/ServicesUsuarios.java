package org.quevedo.client.services;

import io.vavr.control.Either;
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

    public Either<String, List<UsuarioGetDTO>> getAllUsers() {
        return daoUsuarios.getAllUsers();
    }

    public Either<String, UsuarioGetDTO> registrarUsuarioAdmin(UsuarioRegisterDTO usuarioRegisterDTO) {
        return daoUsuarios.registrarUsuarioAdmin(usuarioRegisterDTO);
    }

    public Either<String, UsuarioGetDTO> registrarUsuarioNormal(UsuarioRegisterDTO usuarioRegisterDTO) {
        return daoUsuarios.registrarUsuarioNormal(usuarioRegisterDTO);
    }

    public Either<String, Void> changePassword(String changePasswordEmail) {
        return daoUsuarios.changePassword(changePasswordEmail);
    }

    public Either<String, LoginResultDTO> doLogin(String correo, String password) {
        return daoUsuarios.doLogin(correo, password);
    }
}
