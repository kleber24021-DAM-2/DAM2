package com.example.seriesfollower.data.model.series.episode


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "stills")
    val stills: List<Still>
)