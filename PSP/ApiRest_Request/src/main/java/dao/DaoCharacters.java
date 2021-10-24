package dao;

import dao.models.characters.CharacterResponse;
import dao.models.characters.RickMortyCharacter;
import dao.retrofit.RickAndMortyApi;
import dao.utils.Singleton_RetroFit;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCharacters {

    public RickMortyCharacter getCharacterById(int id) {
        Retrofit retro = Singleton_RetroFit.getInstance();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        try {
            Response<RickMortyCharacter> response = api.getCharacterById(id).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException ioException) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ioException);
        }
        return null;
    }

    public CharacterResponse getFilteredCharacters(String name, String status, String species, String gender, int page) {
        Retrofit retro = Singleton_RetroFit.getInstance();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        try {
            Response<CharacterResponse> response = api.getCharactersByAll(name, status, species, gender, page).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
        return null;
    }
}
