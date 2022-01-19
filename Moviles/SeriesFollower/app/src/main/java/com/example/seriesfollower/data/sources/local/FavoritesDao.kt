package com.example.seriesfollower.data.sources.local

import androidx.room.*
import com.example.seriesfollower.data.models.localmodels.MovieEntity
import com.example.seriesfollower.data.models.localmodels.SeriesEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(toInsert: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteSeries(toInsert: SeriesEntity)

    @Query("SELECT * FROM MOVIES")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM SERIES")
    suspend fun getAllSeries(): List<SeriesEntity>

    @Query("SELECT * FROM MOVIES WHERE MOVIE_ID = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("SELECT * FROM SERIES WHERE SERIES_ID = :seriesId")
    suspend fun getSeriesById(seriesId: Int): SeriesEntity

    @Query("SELECT EXISTS(SELECT * FROM MOVIES WHERE MOVIE_ID = :movieId)")
    suspend fun favoriteMovieExists(movieId: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM SERIES WHERE SERIES_ID = :seriesId)")
    suspend fun favoriteSeriesExists(seriesId: Int): Boolean

    @Delete
    suspend fun deleteMovie(toDelete: MovieEntity)

    @Delete
    suspend fun deleteSeries(toDelete: SeriesEntity)

}