package com.example.roomproject.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SuperHeroWithComicsSeries (
    @Embedded val superHeroComic : SuperHeroesWithComics,
    @Relation(
        parentColumn = "HERO_ID",
        entityColumn = "SUPERHERO_ID"
    )
    val series:List<SeriesEntity>?
        )