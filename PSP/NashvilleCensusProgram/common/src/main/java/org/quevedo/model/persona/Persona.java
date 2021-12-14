package org.quevedo.model.persona;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persona {
    private String nombre;
    private String personalId;
    private EstadoCivil estadoCivil;
    private Sexo sexo;
    private LocalDate fechaNacimiento;
    private String lugarProcedencia;
    private String idPersonaCasada;
    private List<Persona> hijos;

    public void addHijo(Persona hijo){
        hijos.add(hijo);
    }

    public Persona(Persona persona){
        this.nombre = persona.getNombre();
        this.personalId = persona.getPersonalId();
        this.estadoCivil = persona.getEstadoCivil();
        this.sexo = persona.getSexo();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.lugarProcedencia = persona.getLugarProcedencia();
        this.idPersonaCasada = persona.getIdPersonaCasada();
        this.hijos = persona.getHijos();
    }
}

