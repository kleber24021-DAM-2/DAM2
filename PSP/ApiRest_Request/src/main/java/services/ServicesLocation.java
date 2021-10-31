package services;

import dao.DaoLocations;
import dao.models.ownmodels.OwnLocation;
import io.vavr.control.Either;

import javax.inject.Inject;

public class ServicesLocation {

    private final DaoLocations dao;

    @Inject
    public ServicesLocation(DaoLocations dao) {
        this.dao = dao;
    }

    public Either<String, OwnLocation> getLocation(int id) {
        return dao.getLocationById(id);
    }

    public Either<String, OwnLocation> getLocation(String url) {
        String[] splitUrl = url.split("/");
        return getLocation(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }
}
