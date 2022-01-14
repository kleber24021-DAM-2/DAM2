package com.example.seriesfollower.data.model.series.season


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "posters")
    val posters: List<Poster>
)