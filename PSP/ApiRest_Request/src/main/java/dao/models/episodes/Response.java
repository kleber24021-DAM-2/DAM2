package dao.models.episodes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("results")
    private List<Episode> results;

    @SerializedName("info")
    private dao.models.Info info;

    public List<Episode> getResults() {
        return results;
    }

    public dao.models.Info getInfo() {
        return info;
    }
}