package com.example.seriesfollower.domain.series.episode

import java.time.LocalDate

data class OwnEpisode (
    val id:Int,
    val name:String,
    val airDate:LocalDate,
    val images: List<String>,
    val seasonNum:Int,
    val videos: List<String>,
    val castList: List<CastMember>
)