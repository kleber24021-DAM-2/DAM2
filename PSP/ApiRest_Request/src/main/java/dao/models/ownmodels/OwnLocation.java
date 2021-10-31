package dao.models.ownmodels;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OwnLocation {
    private String name;
    private int id;
    private String type;
    private String dimension;
    private List<String> residents;
}
