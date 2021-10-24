package dao.retrofit;

import dao.models.characters.CharacterResponse;
import dao.models.characters.RickMortyCharacter;
import dao.models.episodes.Episode;
import dao.models.locations.Location;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RickAndMortyApi {

    //Call of characters
    @GET("character/{id}")
    Call<RickMortyCharacter> getCharacterById(@Path("id") int id);

    @GET("character/")
    Call<CharacterResponse> getCharactersByAll(@Query("name") String name,
                                               @Query("status") String status,
                                               @Query("species") String species,
                                               @Query("gender") String gender,
                                               @Query("page") int page);

    //Call of Locations
    @GET("location/{id}")
    Call<Location> getLocationById(@Path("id") int id);

    //Call of Episodes
    @GET("episode/{id}")
    Call<Episode> getEpisodeById(@Path("id") int id);
}
