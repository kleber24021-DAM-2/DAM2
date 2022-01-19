package com.example.seriesfollower.domain.model.queryresult

data class OwnResult(
    val id: Int,
    val title: String,
    val popularity: Double,
    val voteAverage: Double,
    val resultType: ResultType,
    val mainImage: String,
)