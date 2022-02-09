package org.quevedo.secretkeeper.model;

import lombok.Data;

@Data
public class Secret {
    private int id;
    private String message;
    private String owner;
}
