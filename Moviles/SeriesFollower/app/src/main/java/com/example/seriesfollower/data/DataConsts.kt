package com.example.seriesfollower.data

import java.time.format.DateTimeFormatter

object DataConsts {
    const val YOUTUBE_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE = "YouTube"
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val IO_DISPATCHER = "ioDispatcher"
}