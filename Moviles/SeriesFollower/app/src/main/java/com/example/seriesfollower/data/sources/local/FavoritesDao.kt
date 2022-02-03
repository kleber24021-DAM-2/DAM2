package com.example.seriesfollower.data.sources.local

import androidx.room.*
import com.example.seriesfollower.data.models.localmodels.MovieEntity
import com.example.seriesfollower.data.models.localmodels.SeriesEntity
import com.example.seriesfollower.data.models.localmodels.TrendingResultsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(toInsert: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteSeries(toInsert: SeriesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrendingResults(toInsert: List<TrendingResultsEntity>)

    @Query("SELECT * FROM MOVIES")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM SERIES")
    fun getAllSeries(): Flow<List<SeriesEntity>>

    @Query("SELECT * FROM MOVIES WHERE MOVIE_ID = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity

    @Query("SELECT * FROM SERIES WHERE SERIES_ID = :seriesId")
    fun getSeriesById(seriesId: Int): SeriesEntity

    @Query("SELECT EXISTS(SELECT * FROM MOVIES WHERE MOVIE_ID = :movieId)")
    suspend fun favoriteMovieExists(movieId: Int): Boolean

    @Query("SELECT EXISTS(SELECT * FROM SERIES WHERE SERIES_ID = :seriesId)")
    suspend fun favoriteSeriesExists(seriesId: Int): Boolean

    @Query("SELECT * FROM TRENDING")
    fun getAllTrending() : List<TrendingResultsEntity>

    @Query("DELETE FROM TRENDING")
    suspend fun deleteAllTrendings()

    @Delete
    suspend fun deleteMovie(toDelete: MovieEntity)

    @Delete
    suspend fun deleteSeries(toDelete: SeriesEntity)

}