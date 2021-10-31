package services;

import dao.DaoEpisodes;
import dao.models.ownmodels.OwnEpisode;
import io.vavr.control.Either;

import javax.inject.Inject;

public class ServicesEpisodes {

    private final DaoEpisodes dao;

    @Inject
    public ServicesEpisodes(DaoEpisodes dao) {
        this.dao = dao;
    }

    public Either<String, OwnEpisode> getEpisode(int id) {
        return dao.getEpisodeByID(id);
    }

    public Either<String, OwnEpisode> getEpisode(String url) {
        String[] splitUrl = url.split("/");
        return getEpisode(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }
}
