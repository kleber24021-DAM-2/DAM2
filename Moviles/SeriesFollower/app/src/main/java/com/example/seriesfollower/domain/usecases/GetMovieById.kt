package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieById @Inject constructor(private val repository: MoviesRepository) {
    suspend fun invoke(movieId: Int) = repository.getOnlineMovieById(movieId)
}