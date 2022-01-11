package org.quevedo.client.services;

import io.vavr.control.Either;
import org.quevedo.client.dao.implementations.DaoJornada;
import org.quevedo.sharedmodels.Jornada;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class ServiceJornada {
    private final DaoJornada daoJornada;

    @Inject
    public ServiceJornada(DaoJornada daoJornada) {
        this.daoJornada = daoJornada;
    }

    public Either<String, List<Jornada>> getAllJornadas() {
        return daoJornada.getAllJornadas();
    }

    public Either<String, Jornada> addJornada(LocalDate jornadaDate) {
        String stringDate = jornadaDate.format(ServiceConstants.dateTimeFormatter);
        return daoJornada.addJornada(stringDate);
    }
}
