package org.quevedo.sharedmodels.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioGetDTO {
    private int id;
    private String correo;
    private TipoUsuario tipoUsuario;
}
