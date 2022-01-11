package org.quevedo.sharedmodels.usuario;

import java.util.Arrays;

public enum TipoUsuario {
    ADMIN(1), NORMAL(2);

    private final int num;

    TipoUsuario(int num){
        this.num = num;
    }

    public int getNum(){
        return num;
    }

    public static TipoUsuario get (int num){
        return Arrays.stream(TipoUsuario.values())
                .filter(tipoUsuario -> tipoUsuario.num == num)
                .findFirst().orElse(null);
    }
}
