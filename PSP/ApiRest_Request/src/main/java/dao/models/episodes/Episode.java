package dao.models.episodes;

import com.google.gson.annotations.SerializedName;
import dao.models.ownmodels.OwnEpisode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

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


    public OwnEpisode toOwnModel(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, u", Locale.ENGLISH);
        return new OwnEpisode(id,
                name,
                episode,
                LocalDate.parse(airDate, formatter),
                characters);
    }
}