package org.quevedo.sharedmodels.partido;

import lombok.Builder;
import lombok.Data;
import org.quevedo.sharedmodels.Equipo;
import org.quevedo.sharedmodels.Jornada;

@Data
@Builder
public class Partido {
    private int idPartido;
    private Jornada jornadaPartido;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int resultadoLocal;
    private int resultadoVisitante;
}
