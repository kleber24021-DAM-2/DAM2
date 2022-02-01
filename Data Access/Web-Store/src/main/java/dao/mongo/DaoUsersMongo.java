package dao.mongo;

import com.mongodb.client.MongoCollection;
import dao.interfaces.DAOUsers;
import dao.utils.MongoModule;
import lombok.extern.log4j.Log4j2;
import model.converters.UserConverters;
import model.user.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@Log4j2
public class DaoUsersMongo implements DAOUsers {

    MongoCollection<Document> usersCollection = MongoModule.getMongoInstance().getCollection("Users");

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User t) {
        return null;
    }

    @Override
    public void update(User t) {

    }

    @Override
    public boolean delete(User t) {
        return false;
    }

    @Override
    public User validateUserPassword(User t) {
        UserConverters userConverters = new UserConverters();
        User fetchedUser = null;
        try{
            fetchedUser = usersCollection
                    .find(eq("username", t.getUsername()))
                    .into(new ArrayList<>())
                    .stream()
                    .map(userConverters::convertDocumentToUser)
                    .findFirst().get();

            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);

            if (!fetchedUser.getPassword().equals(t.getPassword())){
                fetchedUser = null;
            }
        }catch (NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(), noSuchElementException);
        }
        return fetchedUser;
    }
}
