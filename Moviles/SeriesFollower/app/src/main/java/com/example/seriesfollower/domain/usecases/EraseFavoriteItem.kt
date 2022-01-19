package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toEntityModel
import com.example.seriesfollower.data.models.localmodels.toMovieEntity
import com.example.seriesfollower.data.models.localmodels.toSeriesEntity
import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class EraseFavoriteItem @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(changeFavorite: OwnSeries) {
        repository.deleteFavSeries(changeFavorite.toEntityModel())
    }

    suspend fun invoke(changeFavorite: OwnMovie) {
        repository.deleteFavMovie(changeFavorite.toEntityModel())
    }

    suspend fun invoke(deleteFav: FavoriteItem) {
        when (deleteFav.itemType) {
            ResultType.MOVIE -> repository.deleteFavMovie(deleteFav.toMovieEntity())
            ResultType.TV -> repository.deleteFavSeries(deleteFav.toSeriesEntity())
        }
    }
}