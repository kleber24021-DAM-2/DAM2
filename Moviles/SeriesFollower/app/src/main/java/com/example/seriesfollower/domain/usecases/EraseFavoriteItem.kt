package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toEntityModel
import com.example.seriesfollower.data.models.localmodels.toMovieEntity
import com.example.seriesfollower.data.models.localmodels.toSeriesEntity
import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import javax.inject.Inject

class EraseFavoriteItem @Inject constructor(private val moviesRepository: MoviesRepository, private val seriesRepository: SeriesRepository) {
    suspend fun invoke(changeFavorite: OwnSeries) {
        seriesRepository.deleteFavSeries(changeFavorite.toEntityModel())
    }

    suspend fun invoke(changeFavorite: OwnMovie) {
        moviesRepository.deleteFavMovie(changeFavorite.toEntityModel())
    }

    suspend fun invoke(deleteFav: FavoriteItem) {
        when (deleteFav.itemType) {
            ResultType.MOVIE -> moviesRepository.deleteFavMovie(deleteFav.toMovieEntity())
            ResultType.TV -> seriesRepository.deleteFavSeries(deleteFav.toSeriesEntity())
        }
    }
}