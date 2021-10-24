package dao;

import dao.models.episodes.Episode;
import dao.retrofit.RickAndMortyApi;
import dao.utils.Singleton_RetroFit;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoEpisodes {
    public Episode getEpisodeByID(int id) {
        Retrofit retro = Singleton_RetroFit.getInstance();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        try {
            Response<Episode> episode = api.getEpisodeById(id).execute();
            if (episode.isSuccessful()) {
                return episode.body();
            }
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
        return null;
    }
}
