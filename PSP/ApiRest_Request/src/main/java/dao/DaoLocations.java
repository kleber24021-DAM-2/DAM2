package dao;

import dao.models.locations.Location;
import dao.retrofit.RickAndMortyApi;
import dao.utils.Singleton_RetroFit;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLocations {
    public Location getLocationById(int id) {
        Retrofit retro = Singleton_RetroFit.getInstance();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);

        try {
            Response<Location> location = api.getLocationById(id).execute();
            if (location.isSuccessful()) {
                return location.body();
            }
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, io);
        }
        return null;
    }
}
