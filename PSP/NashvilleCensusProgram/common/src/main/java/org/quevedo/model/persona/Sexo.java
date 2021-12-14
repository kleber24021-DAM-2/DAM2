package org.quevedo.model.persona;

public enum Sexo {
    HOMBRE("Hombre"),
    MUJER("Mujer");

    private final String text;
    Sexo(final String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
