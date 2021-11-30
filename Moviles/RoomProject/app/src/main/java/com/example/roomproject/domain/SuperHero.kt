package com.example.roomproject.domain

import java.time.LocalDate

data class SuperHero(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val modifiedDate: LocalDate,
    val comicsList: List<Comic>?,
    val seriesList: List<Series>?
)
