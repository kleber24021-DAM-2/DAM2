package org.quevedo.common.models;

import lombok.Data;

import java.util.List;

@Data
public class Secreto {
    private String id;
    private String message;
    private String secretName;
    private List<Usuario> sharedWith;
}
