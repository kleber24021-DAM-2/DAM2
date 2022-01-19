package com.example.seriesfollower.data

import java.time.format.DateTimeFormatter

object DataConsts {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val NO_IMAGE_URL = "https://demofree.sirv.com/nope-not-here.jpg"
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val IO_DISPATCHER = "ioDispatcher"
    const val DATABASE_NAME = "favorites_database"
}