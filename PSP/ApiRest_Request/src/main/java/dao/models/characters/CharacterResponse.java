package dao.models.characters;

import dao.models.Info;

import java.util.List;

public class CharacterResponse {
    private List<RickMortyCharacter> results;
    private Info info;

    public List<RickMortyCharacter> getResults() {
        return results;
    }

    public Info getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "results = '" + results + '\'' +
                        ",info = '" + info + '\'' +
                        "}";
    }
}