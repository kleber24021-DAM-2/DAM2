package services;

import dao.DaoCharacters;
import dao.models.ownmodels.OwnCharacter;
import io.vavr.Tuple2;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class ServicesCharacter {

    private final DaoCharacters dao;

    @Inject
    public ServicesCharacter(DaoCharacters dao) {
        this.dao = dao;
    }

    public Either<String, OwnCharacter> getCharacterByID(int id) {
        return dao.getCharacterById(id);
    }

    public Either<String, OwnCharacter> getCharacterByURL(String url) {
        String[] splitUrl = url.split("/");
        return dao.getCharacterById(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }

    public Either<String, Tuple2<Integer, List<OwnCharacter>>> getFilteredCharacters(String name, String status, String species, String gender, int page) {
        return dao.getFilteredCharacters(name, status, species, gender, page);
    }
}
