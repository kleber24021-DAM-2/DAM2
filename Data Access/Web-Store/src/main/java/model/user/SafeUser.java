package model.user;

public class SafeUser {
//    El SafeUser no contiene la contraseña, este será el objeto que se guardará a modo de sesión
//    Para tener una referencia al id y el nombre de usuario
    int id;
    String username;

    public SafeUser(int id,String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
