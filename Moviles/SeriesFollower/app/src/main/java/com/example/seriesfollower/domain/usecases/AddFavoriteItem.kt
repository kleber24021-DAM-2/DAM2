package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toEntityModel
import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class AddFavoriteItem @Inject constructor(private val moviesRepository: MoviesRepository, private val seriesRepository: SeriesRepository) {
    suspend fun invoke(changeFavorite: OwnSeries) {
        seriesRepository.insertFavoriteSeries(changeFavorite.toEntityModel())
    }

    suspend fun invoke(changeFavorite: OwnMovie) {
        moviesRepository.insertFavoriteMovie(changeFavorite.toEntityModel())
    }

}