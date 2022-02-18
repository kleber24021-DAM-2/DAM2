package org.quevedo.controllers;

import org.quevedo.models.hibernate.EntityPermisos;
import org.quevedo.models.hibernate.EntityPermisosObjetos;
import org.quevedo.services.ObjetosService;
import org.quevedo.services.PermisosObjetoService;
import org.quevedo.services.PermisosService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/* 1. (0.75) Add permissions to users on specific objects (Object_Permissions table) */
public class Exercise1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PermisosObjetoService permisosObjetoService = new PermisosObjetoService();
        ObjetosService objetosService = new ObjetosService();

        PermisosService permisosService = new PermisosService();
        List<EntityPermisos> listaPermisos = new ArrayList<>();
        permisosService.getAllPermisos()
                .peek(listaPermisos::addAll)
                .peekLeft(System.err::println);


        System.out.println("Selecciona el id del usuario");
        int idUsuario = scanner.nextInt();

        System.out.println("Selecciona el id del objeto");
        int idObjeto = scanner.nextInt();

        listaPermisos.forEach(System.out::println);
        System.out.println("Selecciona uno de los permisos");
        int idPermiso = scanner.nextInt();

        EntityPermisosObjetos permisosObjeto = new EntityPermisosObjetos();
        permisosObjeto.setIdUsuario(idUsuario);
        permisosObjeto.setPermisosByIdPermiso(listaPermisos.get(idPermiso - 1));
        permisosObjeto.setObjetosByIdObjeto(objetosService.getObjetoById(idObjeto).get());

        permisosObjetoService.addObjectPermission(permisosObjeto)
                .peek(System.out::println)
                .peekLeft(System.err::println);


    }
}
