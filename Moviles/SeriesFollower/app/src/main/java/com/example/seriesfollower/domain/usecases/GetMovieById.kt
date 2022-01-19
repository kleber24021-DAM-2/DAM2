package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import javax.inject.Inject

class GetMovieById @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(movieId: Int) = repository.getOnlineMovieById(movieId)
}