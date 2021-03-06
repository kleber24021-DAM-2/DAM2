package com.example.seriesfollower.data.models.apimodels

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.models.apimodels.movie.ApiMovie
import com.example.seriesfollower.data.models.apimodels.query.ApiQuery
import com.example.seriesfollower.data.models.apimodels.series.general.ApiSeries
import com.example.seriesfollower.data.models.apimodels.series.general.NextEpisodeToAir
import com.example.seriesfollower.data.models.apimodels.series.general.Season
import com.example.seriesfollower.data.models.apimodels.series.season.ApiSeason
import com.example.seriesfollower.data.models.apimodels.series.season.Episode
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.queryresult.OwnResult
import com.example.seriesfollower.domain.model.queryresult.QueryInfo
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.domain.model.series.episode.OwnEpisode
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import com.example.seriesfollower.domain.model.series.season.OwnSeason
import java.time.LocalDate

const val MEDIATYPE_TV = "tv"
const val NOT_FOUND = "Not found"
const val UNKNNOWN = "Unknown"
const val EMPTY = ""

fun ApiMovie.toOwnModel(): OwnMovie {
    return OwnMovie(
        id,
        title,
        overview,
        getImageUrl(posterPath),
        LocalDate.parse(releaseDate, DataConsts.DATE_FORMATTER),
        voteAverage,

        )
}

fun ApiQuery.toOwnQuery(): QueryInfo {
    return QueryInfo(
        page,
        totalPages,
        totalResults,
        results.map { result ->
            val mediaType: ResultType
            val title: String
            if (result.mediaType == MEDIATYPE_TV) {
                mediaType = ResultType.TV
                title = result.name ?: NOT_FOUND
            } else {
                mediaType = ResultType.MOVIE
                title = result.title ?: NOT_FOUND
            }

            OwnResult(
                result.id,
                title,
                result.popularity ?: -1.0,
                result.voteAverage ?: -1.0,
                mediaType,
                result.posterPath.let {
                    if (it != null) {
                        getImageUrl(it)
                    } else {
                        DataConsts.NO_IMAGE_URL
                    }
                }
            )
        }
    )
}

fun ApiSeries.toOwnSeries(): OwnSeries {
    return OwnSeries(
        name,
        id,
        getImageUrl(posterPath),
        genres.map { it.name },
        nextEpisodeToAir?.toOwnEpisode(),
        homepage,
        numberOfEpisodes,
        seasons.map {
               it.toOwnSeason()
        },
        overview,
        popularity,
        voteAverage,

        )
}

fun Season.toOwnSeason(): OwnSeason{
    return OwnSeason(
        id, name, seasonNumber)
}

fun ApiSeason.toListOfEpisodes():List<OwnEpisode>{
    return episodes!!.map {episode ->
        OwnEpisode(
            episode.id?:-1,
            episode.name?: UNKNNOWN,
            LocalDate.parse(episode.airDate, DataConsts.DATE_FORMATTER),
            posterPath?: EMPTY,
            episode.seasonNumber!!
        )
    }
}

fun NextEpisodeToAir.toOwnEpisode(): OwnEpisode {
    return OwnEpisode(
        id,
        name,
        LocalDate.parse(airDate, DataConsts.DATE_FORMATTER),
        getImageUrl(stillPath ?: EMPTY),
        seasonNumber
    )
}

private fun getImageUrl(path: String): String {
    return DataConsts.IMAGE_URL + path
}
