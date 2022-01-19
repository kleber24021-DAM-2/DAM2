package com.example.seriesfollower.domain.model.favorite

import com.example.seriesfollower.domain.model.queryresult.ResultType
import java.time.LocalDate

data class FavoriteItem(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val itemType: ResultType,
    val nextEpisodeDate: LocalDate?
)