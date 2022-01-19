package com.example.seriesfollower.domain.model.series.general

import com.example.seriesfollower.domain.model.series.episode.OwnEpisode

data class OwnSeries(
    val title: String,
    val id: Int,
    val mainImage: String,
    val genres: List<String>,
    val nextEpisode: OwnEpisode?,
    val homePage: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
)