package dao.models.episodes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Episode {

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("characters")
    private List<String> characters;

    @SerializedName("created")
    private String created;

    @SerializedName("name")
    private String name;

    @SerializedName("episode")
    private String episode;

    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    public String getAirDate() {
        return airDate;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public String getEpisode() {
        return episode;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}