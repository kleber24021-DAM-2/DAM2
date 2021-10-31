package dao.models.ownmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class OwnEpisode {
    private int id;
    private String name;
    private String episode;
    private LocalDate airDate;
    private List<String> characters;
}
