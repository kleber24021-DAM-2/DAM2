package com.quevedo.footballvideos.data.models.video


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "competition")
    val competition: String?,
    @Json(name = "competitionUrl")
    val competitionUrl: String?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "matchviewUrl")
    val matchviewUrl: String?,
    @Json(name = "thumbnail")
    val thumbnail: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "videos")
    val videos: List<Video>?
)