package org.quevedo.sharedmodels.usuario;

import lombok.Data;

@Data

public class UsuarioRegisterDTO {
    private String correo;
    private String password;
    private String passwordConfirmation;
    private TipoUsuario tipoUsuario;

}
