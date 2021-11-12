package com.example.roomproject.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SuperHeroesWithComicsSeries(
    @Embedded val superHeroe: SuperHeroEntity,
    @Relation(
        parentColumn = "HERO_ID",
        entityColumn = "SUPERHERO_ID"
    )
    val comics:List<ComicEntity>?,
    @Relation(
        parentColumn = "HERO_ID",
        entityColumn = "SUPERHERO_ID"
    )
    val series:List<SeriesEntity>?
)