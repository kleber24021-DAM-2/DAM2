package dao;

import dao.models.episodes.Episode;
import dao.models.ownmodels.OwnEpisode;
import dao.retrofit.RickAndMortyApi;
import dao.utils.SingletonRetroFit;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class DaoEpisodes {

    private final SingletonRetroFit singletonRetroFit;

    @Inject
    public DaoEpisodes(SingletonRetroFit singletonRetroFit) {
        this.singletonRetroFit = singletonRetroFit;
    }

    public Either<String, OwnEpisode> getEpisodeByID(int id) {
        Either<String, OwnEpisode> result;
        Retrofit retro = singletonRetroFit.getRetrofit();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        try {
            Response<Episode> episode = api.getEpisodeById(id).execute();
            if (episode.isSuccessful() && episode.body() != null) {
                result = Either.right(episode.body().toOwnModel());
            } else {
                result = Either.left(episode.errorBody().toString());
            }
        } catch (IOException io) {
            log.error(io.getMessage(), io);
            result = Either.left(io.getMessage());
        }
        return result;
    }
}
