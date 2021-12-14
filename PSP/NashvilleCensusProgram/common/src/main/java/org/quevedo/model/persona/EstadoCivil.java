package org.quevedo.model.persona;

import java.util.HashMap;
import java.util.Map;

public enum EstadoCivil {
    SOLTERO("Solter@", 1),
    CASADO("Casad@", 2),
    VIUDO("Viud@", 3);

    private final String text;
    private final int value;
    private static final Map<Integer, EstadoCivil> map = new HashMap<>();

    EstadoCivil(final String text, int value){
        this.text = text;
        this.value = value;
    }

    static {
        for (EstadoCivil estadoCivil : EstadoCivil.values()){
            map.put(estadoCivil.value, estadoCivil);
        }
    }

    public static EstadoCivil valueOf(int value){
        return map.get(value);
    }

    public String getText() {
        return text;
    }
    public int getValue(){
        return value;
    }
}
