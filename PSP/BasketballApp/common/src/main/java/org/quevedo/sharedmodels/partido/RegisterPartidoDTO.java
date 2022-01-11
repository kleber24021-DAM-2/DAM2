package org.quevedo.sharedmodels.partido;

import lombok.Data;

@Data
public class RegisterPartidoDTO {
    private int idJornada;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int resultadoLocal;
    private int resultadoVisitante;
}
