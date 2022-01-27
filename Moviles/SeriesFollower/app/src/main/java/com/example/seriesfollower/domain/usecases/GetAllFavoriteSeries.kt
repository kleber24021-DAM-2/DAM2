package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.models.localmodels.toUiModel
import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import javax.inject.Inject

class GetAllFavoriteSeries @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend fun invoke() = seriesRepository.getAllFavSeries().map { it.toUiModel() }
}