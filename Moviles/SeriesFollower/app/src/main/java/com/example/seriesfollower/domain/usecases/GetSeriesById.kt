package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import javax.inject.Inject

class GetSeriesById @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend fun invoke(seriesId: Int) = seriesRepository.getOnlineSeriesById(seriesId)
}