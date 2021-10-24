package services;

import dao.DaoEpisodes;
import dao.models.episodes.Episode;

public class ServicesEpisodes {
    public Episode getEpisode(int id) {
        DaoEpisodes dao = new DaoEpisodes();
        return dao.getEpisodeByID(id);
    }

    public Episode getEpisode(String url) {
        String[] splitUrl = url.split("/");
        return getEpisode(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }
}
