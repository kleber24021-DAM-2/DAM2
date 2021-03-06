package org.quevedo.sharedmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private int idEquipo;
    private String nombre;
}
