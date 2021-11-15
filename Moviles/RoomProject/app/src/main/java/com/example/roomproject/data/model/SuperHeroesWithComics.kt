package com.example.roomproject.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SuperHeroesWithComics(
    @Embedded val superHeroe: SuperHeroEntity,
    @Relation(
        parentColumn = "HERO_ID",
        entityColumn = "SUPERHERO_ID"
    )
    val comics:List<ComicEntity>?,
)