package org.quevedo.common.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Secreto {
    private int id;
    private String message;
    private String secretName;
    private String secretOwner;
    private String signature;
    //Un mapa entre el usuario y su clave cifrada
    private Map<Usuario, String> sharedWith;

    public SecretoDTO toSecretoDTO() {
        SecretoDTO secretoDTO = new SecretoDTO();
        secretoDTO.setId(id);
        secretoDTO.setMessage(message);
        secretoDTO.setSecretOwner(secretOwner);
        secretoDTO.setSignature(signature);
        Map<String, String> newSharedWith = new HashMap<>();
        sharedWith.forEach((usuario, s) -> newSharedWith.put(Integer.toString(usuario.getId()), s));
        secretoDTO.setSharedWith(newSharedWith);
        return secretoDTO;
    }
}
