package com.example.roomproject.domain

import java.time.LocalDate


data class Comic(
    val id: Int,
    val name: String,
    val publishedDate: LocalDate
)