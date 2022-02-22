package com.quevedo.footballvideos.data.models.video


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    @Json(name = "embed")
    val embed: String?,
    @Json(name = "title")
    val title: String?
)