package com.quevedo.footballvideos.domain.models

import java.time.LocalDateTime

data class OwnFootballVideo(
    val competition: String,
    val date: LocalDateTime,
    val thumbnail: String,
    val title: String,
    val videos: List<String>
)
