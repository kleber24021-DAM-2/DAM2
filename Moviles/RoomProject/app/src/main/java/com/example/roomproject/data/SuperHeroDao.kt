package com.example.roomproject.data

import androidx.room.*
import com.example.roomproject.data.model.ComicEntity
import com.example.roomproject.data.model.SeriesEntity
import com.example.roomproject.data.model.SuperHeroEntity
import com.example.roomproject.data.model.SuperHeroWithComicsSeries

@Dao
interface SuperHeroDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createHeroWithoutComics(toInsert: SuperHeroEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createComic(toInsert: ComicEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createSeries(toInsert: SeriesEntity)

    @Transaction
    suspend fun createHeroWithComicsAndSeries(toInsert: SuperHeroWithComicsSeries) {
        toInsert.superHeroComic.superHeroe.id =
            createHeroWithoutComics(toInsert.superHeroComic.superHeroe).toInt()
        toInsert.series?.onEach {
            it.superHeroId = toInsert.superHeroComic.superHeroe.id
            createSeries(it)
        }
        toInsert.superHeroComic.comics?.onEach {
            it.superHeroId = toInsert.superHeroComic.superHeroe.id
            createComic(it)
        }
    }

    @Query("SELECT * FROM HEROES WHERE HERO_ID = :id")
    suspend fun getSimpleSuperHero(id: Int): SuperHeroEntity

    @Query("SELECT * FROM HEROES WHERE HERO_ID = :id")
    suspend fun getSuperHeroWithComicsSeries(id: Int): SuperHeroWithComicsSeries

    @Query("SELECT * FROM HEROES ORDER BY HERO_ID")
    suspend fun getSimpleSuperHeroes(): List<SuperHeroEntity>

    @Update
    suspend fun updateHeroe(toUpdate: SuperHeroEntity)

    @Delete
    suspend fun deleteHero(toDelete: SuperHeroEntity)

    @Delete
    suspend fun deleteComic(toDelete: ComicEntity)

    @Delete
    suspend fun deleteSeries(toDelete: SeriesEntity)

    @Transaction
    suspend fun deleteCompleteHero(id: Int) {
        val toDeleteHero = getSuperHeroWithComicsSeries(id)
        toDeleteHero.superHeroComic.comics?.onEach {
            deleteComic(it)
        }
        toDeleteHero.series?.onEach {
            deleteSeries(it)
        }
        deleteHero(toDeleteHero.superHeroComic.superHeroe)
    }
}