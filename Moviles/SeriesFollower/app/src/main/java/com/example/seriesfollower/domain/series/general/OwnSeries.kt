package com.example.seriesfollower.domain.series.general

import java.time.LocalDate

data class OwnSeries (
    val title:String,
    val id:Int,
    val mainImage:String,
    val genres:List<String>,
    val firstAirDate: LocalDate,
    val homePage: String,
    val numberOfEpisodes:Int,
    val numberOfSeasons:Int,
    val overview:String,
    val popularity:Double,
    val videos: List<String>,
    val voteAverage: Double
    )