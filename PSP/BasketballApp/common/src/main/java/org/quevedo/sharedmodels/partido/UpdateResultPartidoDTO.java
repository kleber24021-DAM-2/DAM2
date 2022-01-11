package org.quevedo.sharedmodels.partido;

import lombok.Data;

@Data
public class UpdateResultPartidoDTO {
    private int idPartido;
    private int resultadoLocal;
    private int resultadoVisitante;
}
