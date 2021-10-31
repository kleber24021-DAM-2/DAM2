package dao.models.characters;

import dao.models.ownmodels.OwnCharacter;

import java.util.List;

public class RickMortyCharacter {
    private String image;
    private String gender;
    private String species;
    private String created;
    private Origin origin;
    private String name;
    private Location location;
    private List<String> episode;
    private int id;
    private String type;
    private String url;
    private String status;

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getSpecies() {
        return species;
    }

    public String getCreated() {
        return created;
    }

    public Origin getOrigin() {
        return origin;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + name;
    }

    public OwnCharacter toOwnModel(){
        return new OwnCharacter(name,
                id,
                image,
                gender,
                species,
                origin.getName(),
                type,
                status,
                location.getName(),
                getLocation().getUrl(),
                episode);
    }
}