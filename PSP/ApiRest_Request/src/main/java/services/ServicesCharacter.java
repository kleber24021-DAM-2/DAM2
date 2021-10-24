package services;

import dao.DaoCharacters;
import dao.models.characters.CharacterResponse;
import dao.models.characters.RickMortyCharacter;

public class ServicesCharacter {
    public RickMortyCharacter getCharacterByID(int id) {
        DaoCharacters dao = new DaoCharacters();
        return dao.getCharacterById(id);
    }

    public RickMortyCharacter getCharacterByURL(String url) {
        String[] splitUrl = url.split("/");
        DaoCharacters dao = new DaoCharacters();
        return dao.getCharacterById(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }

    public CharacterResponse getFilteredCharacters(String name, String status, String species, String gender, int page) {
        DaoCharacters dao = new DaoCharacters();
        return dao.getFilteredCharacters(name, status, species, gender, page);
    }
}
