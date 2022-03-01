package org.quevedo.common.models;

import lombok.Data;

import java.util.Map;

//Creo este secreto, porque al pasar el hashmap de Secreto a través de HTTP se parseaba mal, y perdía los tipos, se quedaba como string
@Data
public class SecretoDTO {
    private int id;
    private String message;
    private String secretName;
    private String secretOwner;
    private String signature;
    //Un mapa entre el usuario y su clave cifrada
    private Map<String, String> sharedWith;
}
