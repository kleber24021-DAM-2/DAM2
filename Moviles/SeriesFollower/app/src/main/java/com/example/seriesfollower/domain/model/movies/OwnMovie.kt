package com.example.seriesfollower.domain.model.movies

import java.time.LocalDate

data class OwnMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val releaseDate: LocalDate,
    val voteAverage: Double,
)