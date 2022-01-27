package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toUiModel
import com.example.seriesfollower.data.repositories.MoviesRepository
import javax.inject.Inject

class GetAllFavoriteMovies @Inject constructor(private val repository: MoviesRepository) {
    suspend fun invoke() = repository.getAllFavMovies().map { it.toUiModel() }
}