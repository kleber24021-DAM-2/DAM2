package org.quevedo.controllers;

import org.quevedo.services.UsuariosService;

import java.util.Scanner;

public class Excercise3 {
    public static void main(String[] args) {
        UsuariosService usuariosService = new UsuariosService();
        System.out.println("Please input user id to delete");
        int userToDelete = new Scanner(System.in).nextInt();
        usuariosService.removeUsuarioById(userToDelete)
                .peek(System.out::println)
                .peekLeft(System.err::println);
    }
}
