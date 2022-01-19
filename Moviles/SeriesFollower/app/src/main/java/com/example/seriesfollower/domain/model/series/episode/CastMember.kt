package com.example.seriesfollower.domain.model.series.episode

data class CastMember(
    val id: Int,
    val name: String,
    val characterName: String,
    val popularity: Double,
    val profileImage: String
)