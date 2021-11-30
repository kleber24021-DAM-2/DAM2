package com.example.roomproject.domain

import java.time.LocalDate

data class Series(
    val id: Int,
    val name: String,
    val publishedDate: LocalDate
)