package dao;

import dao.models.characters.CharacterResponse;
import dao.models.characters.RickMortyCharacter;
import dao.models.ownmodels.OwnCharacter;
import dao.retrofit.RickAndMortyApi;
import dao.utils.SingletonRetroFit;
import gui.utils.UserMessages;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2

public class DaoCharacters {

    public SingletonRetroFit singletonRetroFit;

    @Inject
    public DaoCharacters(SingletonRetroFit retroFit) {
        this.singletonRetroFit = retroFit;
    }

    public Either<String, OwnCharacter> getCharacterById(int id) {
        Either<String, OwnCharacter> result = null;
        Retrofit retro = singletonRetroFit.getRetrofit();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        try {
            Response<RickMortyCharacter> response = api.getCharacterById(id).execute();
            if (response.body() == null) {
                result = Either.left(UserMessages.NULL_BODY);
                return result;
            }
            if (response.isSuccessful()) {
                result = Either.right(response.body().toOwnModel());
            } else {
                result = Either.left(response.errorBody().toString());
            }
        } catch (IOException ioException) {
            log.error(ioException.getMessage(), ioException);
            result = Either.left(ioException.getMessage());
        }
        return result;
    }

    public Either<String, Tuple2<Integer, List<OwnCharacter>>> getFilteredCharacters(String name, String status, String species, String gender, int page) {
        Retrofit retro = singletonRetroFit.getRetrofit();
        RickAndMortyApi api = retro.create(RickAndMortyApi.class);
        Either<String, Tuple2<Integer, List<OwnCharacter>>> eitherResult;
        try {
            Response<CharacterResponse> response = api.getCharactersByAll(name, status, species, gender, page).execute();
            if (response.isSuccessful() && response.body() != null) {
                int totalPages = response.body().getInfo().getPages();
                List<OwnCharacter> result = response.body().getResults().stream().map(RickMortyCharacter::toOwnModel).collect(Collectors.toList());
                eitherResult = Either.right(new Tuple2<>(totalPages, result));
            } else {
                if (response.errorBody() != null) {
                    if (response.code() == 404) {
                        eitherResult = Either.left(UserMessages.EMPTY_QUERY);
                    } else {
                        eitherResult = Either.left(response.errorBody().toString());
                    }
                } else {
                    eitherResult = Either.left(response.errorBody().toString());
                }
            }
        } catch (IOException io) {
            log.error(io.getMessage(), io);
            eitherResult = Either.left(io.getMessage());
        }
        return eitherResult;
    }
}
