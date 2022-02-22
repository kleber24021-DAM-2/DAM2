package com.quevedo.footballvideos.data.models

import com.quevedo.footballvideos.data.models.video.Response
import com.quevedo.footballvideos.domain.models.OwnFootballVideo
import com.quevedo.footballvideos.utils.GeneralConsts
import okhttp3.internal.toImmutableList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Response.toFootballVideo(): OwnFootballVideo {
    return OwnFootballVideo(
        competition ?: GeneralConsts.NOT_AVAILABLE,
        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")),
        thumbnail ?: GeneralConsts.NOT_AVAILABLE,
        title ?: GeneralConsts.NOT_AVAILABLE,
        videos?.map { it.embed ?: GeneralConsts.NOT_AVAILABLE }?.toImmutableList() ?: emptyList()
    )
}