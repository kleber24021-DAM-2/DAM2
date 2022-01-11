package com.example.seriesfollower.data.model.series.episode


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeriesEpisodeFull(
    @Json(name = "air_date")
    val airDate: String,
    @Json(name = "crew")
    val crew: List<Crew>,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    @Json(name = "guest_stars")
    val guestStars: List<GuestStar>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "images")
    val images: Images,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "production_code")
    val productionCode: String,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "still_path")
    val stillPath: String,
    @Json(name = "videos")
    val videos: Videos,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)