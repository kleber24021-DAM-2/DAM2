package org.quevedo.controllers;

import org.quevedo.models.hibernate.EntityPermisosUbicaciones;
import org.quevedo.services.PermisosUbicacionesService;

import java.util.Scanner;

public class Exercise5 {
    public static void main(String[] args) {
        PermisosUbicacionesService service = new PermisosUbicacionesService();
        System.out.println("Please, introduce the name of the location");
        String locationName = new Scanner(System.in).nextLine();

        System.out.println(service.getNumUsuariosByNombreUbicacion(locationName));;
    }
}
