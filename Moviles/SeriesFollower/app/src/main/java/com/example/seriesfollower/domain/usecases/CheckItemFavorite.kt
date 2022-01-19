package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class CheckItemFavorite @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(movie: OwnMovie) =
        repository.isMovieFavorite(movie.id)

    suspend fun invoke(series: OwnSeries) =
        repository.isSeriesFavorite(series.id)
}