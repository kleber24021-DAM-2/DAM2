package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.SeriesRepository
import javax.inject.Inject

class GetAllEpisodesOfSeason @Inject constructor(private val seriesRepository: SeriesRepository) {
    fun invoke(seriesId: Int, season:Int) = seriesRepository.getSeasonEpisodes(seriesId, season)
}