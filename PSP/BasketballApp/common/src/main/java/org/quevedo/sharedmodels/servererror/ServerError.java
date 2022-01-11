package org.quevedo.sharedmodels.servererror;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerError {
    private String errorMensaje;
    private TipoError tipoError;
}
