package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import javax.inject.Inject

class GetSeriesById @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(seriesId: Int) = repository.getOnlineSeriesById(seriesId)
}