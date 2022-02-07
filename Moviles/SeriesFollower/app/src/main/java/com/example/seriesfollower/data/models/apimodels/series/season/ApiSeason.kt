package com.example.seriesfollower.data.models.apimodels.series.season


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSeason(
    @Json(name = "air_date")
    val airDate: String?,
    @Json(name = "episodes")
    val episodes: List<Episode>?,
    @Json(name = "_id")
    val _id: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "images")
    val images: Images?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int?,
    @Json(name = "videos")
    val videos: Videos?
)