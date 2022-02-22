package com.quevedo.footballvideos.data.models.video


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FootballVideo(
    @Json(name = "response")
    val response: List<Response>?
)