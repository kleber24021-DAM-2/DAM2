package com.example.crudfromjson.domain.marvelmodels


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "available")
    val available: Int,
    @Json(name = "collectionURI")
    val collectionURI: String,
    @Json(name = "items")
    val comics: List<Comic>,
    @Json(name = "returned")
    val returned: Int
)