package com.example.seriesfollower.domain.model.series.episode

import java.time.LocalDate

data class OwnEpisode(
    val id: Int,
    val name: String,
    val airDate: LocalDate,
    val image: String,
    val seasonNum: Int
)