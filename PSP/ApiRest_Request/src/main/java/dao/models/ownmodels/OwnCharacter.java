package dao.models.ownmodels;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OwnCharacter {
    private String name;
    private int id;
    private String imageUrl;
    private String gender;
    private String species;
    private String originName;
    private String type;
    private String status;
    private String locationName;
    private String locationUrl;
    private List<String> episodes;

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
