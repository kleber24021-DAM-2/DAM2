package org.quevedo.controllers;

import org.quevedo.models.hibernate.EntityUbicaciones;
import org.quevedo.services.UbicacionService;

import java.util.Scanner;

public class Exercise2 {
    public static void main(String[] args) {
        UbicacionService ubicacionService = new UbicacionService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select an ubication to delete");
        int ubicationId = scanner.nextInt();

        EntityUbicaciones entityUbicaciones = ubicacionService.getUbicacionById(ubicationId).get();
        System.out.println(entityUbicaciones);
        ubicacionService.deleteUbicacion(entityUbicaciones)
                .peek(System.out::println)
                .peekLeft(System.err::println);
    }
}
