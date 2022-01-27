package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class CheckItemFavorite @Inject constructor(private val moviesRepository: MoviesRepository, private val seriesRepository: SeriesRepository) {
    suspend fun invoke(movie: OwnMovie) =
        moviesRepository.isMovieFavorite(movie.id)

    suspend fun invoke(series: OwnSeries) =
        seriesRepository.isSeriesFavorite(series.id)
}