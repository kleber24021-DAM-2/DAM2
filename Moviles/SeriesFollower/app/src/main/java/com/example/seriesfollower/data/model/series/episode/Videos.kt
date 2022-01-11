package com.example.seriesfollower.data.model.series.episode


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    val results: List<Any>
)