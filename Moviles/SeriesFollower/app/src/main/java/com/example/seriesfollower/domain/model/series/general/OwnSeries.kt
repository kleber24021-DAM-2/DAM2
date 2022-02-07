package com.example.seriesfollower.domain.model.series.general

import com.example.seriesfollower.domain.model.series.episode.OwnEpisode
import com.example.seriesfollower.domain.model.series.season.OwnSeason

data class OwnSeries(
    val title: String,
    val id: Int,
    val mainImage: String,
    val genres: List<String>,
    val nextEpisode: OwnEpisode?,
    val homePage: String,
    val numberOfEpisodes: Int,
    val listOfSeasons:List<OwnSeason>,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
)