package org.quevedo.dao;


import org.quevedo.model.persona.EstadoCivil;
import org.quevedo.model.persona.Persona;
import org.quevedo.model.persona.Sexo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DaoPersonas {
    private static final List<Persona> personasNashville = new ArrayList<>();

    static {
        personasNashville.add(
                new Persona("Mario",
                "1",
                EstadoCivil.SOLTERO,
                Sexo.HOMBRE,
                LocalDate.now(),
                "Vallekas",
                null,
                new ArrayList<>()));
        personasNashville.add(
                new Persona("Jorge",
                        "2",
                        EstadoCivil.CASADO,
                        Sexo.HOMBRE,
                        LocalDate.now(),
                        "Leganés",
                        "3",
                        new ArrayList<>()));
        personasNashville.add(
                new Persona("Novia de Jorge",
                        "3",
                        EstadoCivil.CASADO,
                        Sexo.MUJER,
                        LocalDate.now(),
                        "N/A",
                        "2",
                        new ArrayList<>()));
        personasNashville.add(
                new Persona("Óscar",
                        "4",
                        EstadoCivil.CASADO,
                        Sexo.HOMBRE,
                        LocalDate.now(),
                        "Vallekas",
                        "5",
                        List.of(new Persona("Hijo Óscar 1",
                                "6",
                                EstadoCivil.SOLTERO,
                                Sexo.HOMBRE,
                                LocalDate.now(),
                                "Vallekas",
                                null,
                                        new ArrayList<>()),
                                new Persona("Hija Óscar 2",
                                        "7",
                                        EstadoCivil.SOLTERO,
                                        Sexo.MUJER,
                                        LocalDate.now(),
                                        "Vallekas",
                                        null,
                                        new ArrayList<>()))));
        personasNashville.add(
                new Persona("Esposa de Óscar",
                        "5",
                        EstadoCivil.CASADO,
                        Sexo.MUJER,
                        LocalDate.now(),
                        "Vallekas",
                        "4",
                        List.of(new Persona("Hijo Óscar 1",
                                        "6",
                                        EstadoCivil.SOLTERO,
                                        Sexo.HOMBRE,
                                        LocalDate.now(),
                                        "Vallekas",
                                        null,
                                        new ArrayList<>()),
                                new Persona("Hija Óscar 2",
                                        "7",
                                        EstadoCivil.SOLTERO,
                                        Sexo.MUJER,
                                        LocalDate.now(),
                                        "Vallekas",
                                        null,
                                        new ArrayList<>()))));
        personasNashville.add(
                new Persona("Hijo Óscar 1",
                        "6",
                        EstadoCivil.SOLTERO,
                        Sexo.HOMBRE,
                        LocalDate.now(),
                        "Vallekas",
                        null,
                        new ArrayList<>()));
        personasNashville.add(
                new Persona("Hija Óscar 2",
                        "7",
                        EstadoCivil.SOLTERO,
                        Sexo.MUJER,
                        LocalDate.now(),
                        "Vallekas",
                        null,
                        new ArrayList<>()));
    }

    public Persona createPersona(Persona toAdd) {
        int lastId = personasNashville.stream()
                .map(Persona::getPersonalId)
                .map(Integer::valueOf)
                .max(Integer::compareTo).orElse(0);
        String newId = String.valueOf(lastId + 1);
        toAdd.setPersonalId(newId);
        personasNashville.add(toAdd);
        return toAdd;
    }

    public Persona getPersonaById(String id) {
        return personasNashville.stream()
                .filter(persona -> persona.getPersonalId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Persona> getAllPersonas() {
        return Collections.unmodifiableList(personasNashville);
    }

    public List<Persona> filterPersonas(String lugarProcedencia, Integer birthYear, Integer numeroHijos, EstadoCivil estadoCivil) {
        return personasNashville.stream()
                .filter(persona -> {
                    if (lugarProcedencia== null){
                        return true;
                    }else {
                        if (lugarProcedencia.isEmpty() || lugarProcedencia.isBlank()) {
                            return true;
                        } else {
                            return persona.getLugarProcedencia().equalsIgnoreCase(lugarProcedencia);
                        }
                    }
                })
                .filter(persona -> {
                    if (birthYear != null) {
                        return persona.getFechaNacimiento().getYear() == birthYear;
                    } else {
                        return true;
                    }
                })
                .filter(persona -> {
                    if (numeroHijos != null) {
                        int hijos = persona.getHijos().size();
                        return hijos == numeroHijos;
                    } else {
                        return true;
                    }
                })
                .filter(persona -> {
                    if (estadoCivil != null) {
                        return persona.getEstadoCivil().equals(estadoCivil);
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Persona updatePersona(Persona toUpdate) {
        Persona previousePersona = getPersonaById(toUpdate.getPersonalId());
        int index = personasNashville.indexOf(previousePersona);
        if (index != -1) {
            personasNashville.remove(index);
            personasNashville.add(index, toUpdate);
            return toUpdate;
        } else {
            return null;
        }
    }

    public boolean comprobarSiEsHijo(Persona persona) {
        Persona comprobacionHijo = personasNashville.stream()
                .flatMap(persona1 -> persona1.getHijos().stream())
                .filter(hijo -> hijo.getPersonalId().equals(persona.getPersonalId()))
                .findFirst().orElse(null);
        return comprobacionHijo != null;
    }

    public void matarPersona(Persona toKill) {
        if (toKill.getEstadoCivil() == EstadoCivil.CASADO) {
            Persona conyuge = personasNashville.stream()
                    .filter(persona -> persona.getPersonalId().equals(toKill.getIdPersonaCasada()))
                    .findFirst().orElse(null);
            if (conyuge != null) {
                conyuge.setEstadoCivil(EstadoCivil.VIUDO);
                conyuge.setIdPersonaCasada(null);
                updatePersona(conyuge);
            }
        }
        personasNashville.remove(toKill);
    }

    //Este metodo se utiliza solo para exilios y mudanzas. Para las muertes se utiliza el método matarPersona(), que no elimina a la familia y cambia
//    el estado civil del posible conyuge
    public void borrarPersona(Persona toDelete) {
        if (!toDelete.getEstadoCivil().equals(EstadoCivil.SOLTERO)) {
            Persona conyuge = personasNashville.stream()
                    .filter(persona -> toDelete.getIdPersonaCasada().equals(persona.getPersonalId()))
                    .findFirst().get();
            if (conyuge != null) {
                personasNashville.remove(conyuge);
            }
            toDelete.getHijos().forEach(personasNashville::remove);
        }
        personasNashville.remove(toDelete);
    }

    public void casarPersona(String idPersona1, String idPersona2) {
        Persona persona1 = personasNashville.stream()
                        .filter(persona -> persona.getPersonalId().equals(idPersona1))
                                .findFirst().get();
        Persona persona2 = personasNashville.stream()
                        .filter(persona -> persona.getPersonalId().equals(idPersona2))
                                .findFirst().get();
        persona1.setEstadoCivil(EstadoCivil.CASADO);
        persona1.setIdPersonaCasada(persona2.getPersonalId());
        persona2.setEstadoCivil(EstadoCivil.CASADO);
        persona2.setIdPersonaCasada(persona1.getPersonalId());
        updatePersona(persona1);
        updatePersona(persona2);
    }


    public void registrarHijo(String idProgenitor1, String idProgenitor2, Persona hijo) {
        Persona addedHijo = createPersona(hijo);

        Persona progenitor1 = new Persona(getPersonaById(idProgenitor1));
        Persona progenitor2 = new Persona(getPersonaById(idProgenitor2));

        progenitor1.addHijo(addedHijo);
        progenitor2.addHijo(addedHijo);

        updatePersona(progenitor1);
        updatePersona(progenitor2);
    }
}
