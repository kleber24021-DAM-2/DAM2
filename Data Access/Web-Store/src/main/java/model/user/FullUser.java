package model.user;

public class FullUser extends SafeUser{
//    Este user se instanciará únicamente al hacer login para enviarlo al DAO.
//    En principio se "borrará" (cambiar a null) para no guardar la contraseña en memoria.
//    Sé que en la vida real no se hace así, pero quiero hacerlo con algo que tenga algo de sentido
    String password;

    public FullUser(int id,String username,String password) {
        super(id, username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
