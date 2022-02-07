package com.example.seriesfollower.data.models.apimodels.series.season


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Videos(
    @Json(name = "results")
    val results: List<Result>?
)