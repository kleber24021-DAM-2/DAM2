package com.example.seriesfollower.domain.movies

import java.time.LocalDate

data class OwnMovie(
    val id:Int,
    val title:String,
    val tagline:String,
    val genres:List<String>,
    val budget:Int,
    val backdrops: List<String>,
    val posters: List<String>,
    val releaseDate: LocalDate,
    val videos: List<String>,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double
)