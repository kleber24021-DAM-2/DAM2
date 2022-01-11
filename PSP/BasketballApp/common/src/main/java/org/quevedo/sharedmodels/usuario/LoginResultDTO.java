package org.quevedo.sharedmodels.usuario;

import lombok.Data;

@Data
public class LoginResultDTO {
    private boolean isLogged;
    private String correo;
    private String message;
    private TipoUsuario nivelAcceso;
}
