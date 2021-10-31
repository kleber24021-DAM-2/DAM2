package dao;

import dao.models.locations.Location;
import dao.models.ownmodels.OwnLocation;
import dao.retrofit.RickAndMortyApi;
import dao.utils.SingletonRetroFit;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class DaoLocations {

    private final SingletonRetroFit singletonRetroFit;

    @Inject
    public DaoLocations(SingletonRetroFit singletonRetroFit) {
        this.singletonRetroFit = singletonRetroFit;
    }

    public Either<String, OwnLocation> getLocationById(int id) {
        Retrofit retro = singletonRetroFit.getRetrofit();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        Either<String, OwnLocation> result = null;
        try {
            Response<Location> location = api.getLocationById(id).execute();
            if (location.isSuccessful() && location.body() != null) {
                result = Either.right(location.body().toOwnLocation());
            } else {
                result = Either.left(location.errorBody().toString());
            }
        } catch (IOException io) {
            log.error(io.getMessage(), io);
            result = Either.left(io.getMessage());
        }
        return result;
    }
}
