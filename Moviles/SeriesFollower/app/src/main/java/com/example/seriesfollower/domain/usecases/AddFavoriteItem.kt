package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toEntityModel
import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class AddFavoriteItem @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(changeFavorite: OwnSeries) {
        repository.insertFavoriteSeries(changeFavorite.toEntityModel())
    }

    suspend fun invoke(changeFavorite: OwnMovie) {
        repository.insertFavoriteMovie(changeFavorite.toEntityModel())
    }

}