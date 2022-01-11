package org.quevedo.server.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quevedo.sharedmodels.usuario.TipoUsuario;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String correo;
    private String password;
    private String codigoActivacion;
    private boolean isActivo;
    private LocalDateTime fechaActivacion;
    private TipoUsuario tipoUsuario;
}
