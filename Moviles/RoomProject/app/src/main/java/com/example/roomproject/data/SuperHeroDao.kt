package com.example.roomproject.data

import androidx.room.Dao
import androidx.room.Query
import com.example.roomproject.data.model.ComicEntity
import com.example.roomproject.data.model.SeriesEntity
import com.example.roomproject.data.model.SuperHeroEntity
import com.example.roomproject.data.model.SuperHeroesWithComicsSeries

@Dao
interface SuperHeroDao {
    @Query("SELECT * FROM HEROES WHERE HERO_ID = :id")
    suspend fun getSuperHeroWithComics(id: Int):SuperHeroesWithComicsSeries

    @Query("SELECT * FROM HEROES ORDER BY HERO_ID")
    suspend fun getSuperHeroesWithComics(): List<SuperHeroEntity>

    @Query("SELECT * FROM COMICS WHERE SUPERHERO_ID = :id")
    suspend fun getComicsByHero(id: Int): List<ComicEntity>

    @Query("SELECT * FROM SERIES WHERE SUPERHERO_ID = :id")
    suspend fun getSeriesByHero(id: Int): List<SeriesEntity>
}