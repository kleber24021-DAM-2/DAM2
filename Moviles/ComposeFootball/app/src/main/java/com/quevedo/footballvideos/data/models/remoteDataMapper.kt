package com.quevedo.footballvideos.data.models

import com.quevedo.footballvideos.data.models.video.Response
import com.quevedo.footballvideos.domain.models.OwnFootballVideo
import com.quevedo.footballvideos.utils.GeneralConsts
import okhttp3.internal.toImmutableList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Response.toFootballVideo(): OwnFootballVideo {
    val videos = listOf(
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2021-12-10_Retrofit.mp4",
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2022-02-09_ComposeFinal.mp4",
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2021-10-01_RecyclerView.mp4",
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2022-01-19_Flows.mp4",
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2022-01-21_Flows2.mp4",
        "http://informatica.iesquevedo.es/videos/2dam/MOVILES/2021-11-03_RoomViewModel.mp4"
    )
    return OwnFootballVideo(
        competition ?: GeneralConsts.NOT_AVAILABLE,
        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")),
        thumbnail ?: GeneralConsts.NOT_AVAILABLE,
        title ?: GeneralConsts.NOT_AVAILABLE,
        videos.asSequence().shuffled().find { true } ?: "N/A"
    )
}