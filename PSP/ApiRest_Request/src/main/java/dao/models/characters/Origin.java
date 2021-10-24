package dao.models.characters;

public class Origin {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "Origin{" +
                        "name = '" + name + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}
