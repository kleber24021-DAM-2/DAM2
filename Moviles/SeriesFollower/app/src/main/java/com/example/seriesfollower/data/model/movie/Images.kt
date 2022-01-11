package com.example.seriesfollower.data.model.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "backdrops")
    val backdrops: List<Backdrop>,
    @Json(name = "logos")
    val logos: List<Logo>,
    @Json(name = "posters")
    val posters: List<Poster>
)