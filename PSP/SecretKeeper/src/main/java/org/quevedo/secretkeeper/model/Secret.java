package org.quevedo.secretkeeper.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Secret {
    private int id;
    private String message;
    private String secretName;
    private Map<String, String> sharedWith;

    public void addToSharedUsers(String user, String pass) {
        if (sharedWith == null) {
            sharedWith = new HashMap<>();
        }
        sharedWith.put(user, pass);
    }
}
