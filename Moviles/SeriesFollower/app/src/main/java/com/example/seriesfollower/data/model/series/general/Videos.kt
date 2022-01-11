package com.example.seriesfollower.data.model.series.general


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    val results: List<Result>
)