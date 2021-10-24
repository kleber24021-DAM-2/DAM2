package services;

import dao.DaoLocations;
import dao.models.locations.Location;

public class ServicesLocation {
    public Location getLocation(int id) {
        DaoLocations daoLocations = new DaoLocations();
        return daoLocations.getLocationById(id);
    }

    public Location getLocation(String url) {
        String[] splitUrl = url.split("/");
        return getLocation(Integer.parseInt(splitUrl[splitUrl.length - 1]));
    }
}
