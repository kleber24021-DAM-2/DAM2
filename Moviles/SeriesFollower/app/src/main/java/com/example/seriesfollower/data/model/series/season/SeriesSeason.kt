package com.example.seriesfollower.data.model.series.season


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesSeason(
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "episodes")
    val episodes: List<Episode>,
    @Json(name = "_id")
    val id: String,
    @Json(name = "id")
    val seasonId: Int,
    @Json(name = "images")
    val images: Images,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "videos")
    val videos: Videos
)