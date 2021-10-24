package dao.models.locations;

import dao.models.Info;

import java.util.List;

public class ResponseLocations {
    private List<Location> results;
    private Info info;

    public List<Location> getResults() {
        return results;
    }

    public Info getInfo() {
        return info;
    }
}