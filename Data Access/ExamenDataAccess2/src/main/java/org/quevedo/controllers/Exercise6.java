package org.quevedo.controllers;

import org.quevedo.models.hibernate.EntityUbicaciones;
import org.quevedo.services.ServiceMongo;
import org.quevedo.services.UbicacionService;

import java.util.List;

public class Exercise6 {
    public static void main(String[] args) {
        UbicacionService ubicacionService = new UbicacionService();
        ServiceMongo serviceMongo = new ServiceMongo();
        List<EntityUbicaciones> listaUbicaciones = ubicacionService.getAllUbicaciones();
        listaUbicaciones.forEach(System.out::println);

        listaUbicaciones.forEach(serviceMongo::addMongoUbicacion);
    }
}
