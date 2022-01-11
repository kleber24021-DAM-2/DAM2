package org.quevedo.sharedmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jornada {
    private int idJornada;
    private LocalDate fechaJornada;
}
