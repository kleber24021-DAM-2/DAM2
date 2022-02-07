package model.converters;

import model.user.User;
import org.bson.Document;

public class UserConverters {
    public User convertDocumentToUser(Document d){
        return User.builder()
                .password(d.getString("password"))
                .id(d.getString("_id"))
                .username(d.getString("username"))
                .userType(d.getString("userType"))
                .build();
    }

    public Document convertUserToDocument(User user){
        return new Document()
                .append("password", user.getPassword())
                .append("username", user.getUsername())
                .append("userType", user.getUserType());
    }
}
