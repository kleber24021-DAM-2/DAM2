package com.example.seriesfollower.data.models.localmodels

import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import java.time.LocalDate

fun OwnMovie.toEntityModel(): MovieEntity {
    return MovieEntity(
        id, title, overview ,poster, releaseDate, voteAverage
    )
}

fun OwnSeries.toEntityModel(): SeriesEntity {
    return SeriesEntity(
        id, title, nextEpisode?.airDate ,mainImage, overview, voteAverage
    )
}

fun MovieEntity.toUiModel() : FavoriteItem{
    return FavoriteItem(
        id,
        title,
        overview,
        poster,
        releaseDate,
        voteAverage,
        ResultType.MOVIE,
        null
    )
}

fun SeriesEntity.toUiModel() : FavoriteItem{
    return FavoriteItem(
        id,
        title,
        overview,
        mainImage,
        null,
        voteAverage,
        ResultType.TV,
        nextEpisodeToAir
    )
}

fun FavoriteItem.toSeriesEntity() : SeriesEntity{
    return SeriesEntity(
        id,
        title,
        nextEpisodeDate,
        poster,
        overview,
        voteAverage
    )
}

fun FavoriteItem.toMovieEntity() : MovieEntity{
    return MovieEntity(
        id,
        title,
        overview,
        poster,
        releaseDate?: LocalDate.now(),
        voteAverage
    )
}
