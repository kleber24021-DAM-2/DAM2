package com.example.seriesfollower.data.model

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.model.movie.FullMovies
import com.example.seriesfollower.data.model.query.QueryResult
import com.example.seriesfollower.data.model.series.general.SeriesFull
import com.example.seriesfollower.domain.movies.OwnMovie
import com.example.seriesfollower.domain.queryresult.OwnResult
import com.example.seriesfollower.domain.queryresult.QueryInfo
import com.example.seriesfollower.domain.queryresult.ResultType
import com.example.seriesfollower.domain.series.general.OwnSeries
import java.time.LocalDate


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

    fun QueryResult.toQueryInfo(): QueryInfo{
        return QueryInfo(
            page,
            totalPages,
            totalResults,
            results.map {
                val mediaType:ResultType
                val title:String
                if (it.mediaType == "tv"){
                    mediaType = ResultType.TV
                    title = it.name?:"Not found"
                }else{
                    mediaType = ResultType.MOVIE
                    title = it.title?:"Not found"
                }

                OwnResult(
                    it.id,
                    title,
                    it.popularity?:-1.0,
                    it.voteAverage?:-1.0,
                    mediaType,
                    getImageUrl(it.posterPath),
                    false
                )
//                TODO("Need to implement to check if is favorite")
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
