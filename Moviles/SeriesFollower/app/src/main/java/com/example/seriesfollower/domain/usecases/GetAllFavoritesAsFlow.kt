package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toUiModel
import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllFavoritesAsFlow @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository
) {
    suspend fun invoke(): Flow<List<FavoriteItem>> {
        val resultFlow = combine(moviesRepository.getAllFavMovies(), seriesRepository.getAllFavSeries()){ flow1,flow2 ->
            val joinedLists : MutableList<FavoriteItem> = mutableListOf()
            joinedLists.addAll(flow1.map { it.toUiModel() })
            joinedLists.addAll(flow2.map { it.toUiModel() })
            joinedLists
        }.flatMapLatest {
            flow {
                emit(it)
            }
        }
        return resultFlow
    }
}