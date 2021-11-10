package com.example.roomproject.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SuperHeroDao {
    @Query
    suspend fun getSuperHeroWithComics
}