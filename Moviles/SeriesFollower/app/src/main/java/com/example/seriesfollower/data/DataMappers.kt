package com.example.seriesfollower.data

import com.example.seriesfollower.data.model.movie.FullMovies
import com.example.seriesfollower.data.model.query.QueryResult
import com.example.seriesfollower.data.model.series.general.SeriesFull
import com.example.seriesfollower.domain.movies.OwnMovie
import com.example.seriesfollower.domain.queryresult.OwnResult
import com.example.seriesfollower.domain.queryresult.QueryInfo
import com.example.seriesfollower.domain.queryresult.ResultType
import com.example.seriesfollower.domain.series.general.OwnSeries
import java.time.LocalDate

class DataMappers {
    fun FullMovies.toOwnModel(): OwnMovie{
        return OwnMovie(
            id,
            title,
            tagline,
            genres.map{it.name},
            budget,
            images.backdrops.map {
                getImageUrl(it.filePath)
            },
            images.posters.map{
                getImageUrl(it.filePath)
            },
            LocalDate.parse(releaseDate, DataConsts.DATE_FORMATTER),
            videos.results.filter {
                it.site == DataConsts.YOUTUBE
            }.map {
                DataConsts.YOUTUBE_URL + it.key
            },
            voteAverage,
            voteCount,
            popularity
        )
    }

    fun QueryResult.toOwnResult(): QueryInfo{
        return QueryInfo(
            page,
            totalPages,
            totalResults,
            results.map {
                OwnResult(
                    it.id,
                    it.title,
                    it.popularity,
                    it.voteAverage,
                    ResultType.valueOf(it.mediaType),
                    getImageUrl(it.posterPath)
                )
            }
        )
    }

    fun SeriesFull.toOwnSeries(): OwnSeries{
        return OwnSeries(
            name,
            id,
            getImageUrl(backdropPath),
            genres.map { it.name },
            LocalDate.parse(firstAirDate, DataConsts.DATE_FORMATTER),
            homepage,
            numberOfEpisodes,
            numberOfSeasons,
            overview,
            popularity,
            videos.results
                .filter { it.site == DataConsts.YOUTUBE }
                .map { DataConsts.YOUTUBE_URL + it.key },
            voteAverage
        )
    }

    private fun getImageUrl(path: String): String{
        return DataConsts.IMAGE_URL + path
    }
}