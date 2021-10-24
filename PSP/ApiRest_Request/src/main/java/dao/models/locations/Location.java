package dao.models.locations;

import java.util.List;

public class Location {
    private String created;
    private String name;
    private List<String> residents;
    private int id;
    private String type;
    private String dimension;
    private String url;

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public List<String> getResidents() {
        return residents;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDimension() {
        return dimension;
    }

    public String getUrl() {
        return url;
    }
}