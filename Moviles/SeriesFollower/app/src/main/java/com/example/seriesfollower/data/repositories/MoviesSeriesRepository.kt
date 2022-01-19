package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.models.localmodels.MovieEntity
import com.example.seriesfollower.data.models.localmodels.SeriesEntity
import com.example.seriesfollower.data.sources.local.FavoritesDao
import com.example.seriesfollower.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class MoviesSeriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val daoFav: FavoritesDao
) {
    suspend fun getOnlineMovieById(movieId: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getMovieById(movieId)
        }


    suspend fun getMultiByQuery(queryTerm: String, pageNum: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getMultiByQuery(queryTerm, pageNum)

        }

    suspend fun getOnlineSeriesById(seriesId: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getSeriesById(seriesId)

        }

    suspend fun getTrendingResults(page: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getTrendingResults(page)
        }

    suspend fun insertFavoriteMovie(toInsert: MovieEntity) =
        withContext(ioDispatcher) {
            daoFav.insertFavoriteMovie(toInsert)
        }


    suspend fun insertFavoriteSeries(toInsert: SeriesEntity) =
        withContext(ioDispatcher) {
            daoFav.insertFavoriteSeries(toInsert)
        }

    suspend fun getAllFavMovies() =
        withContext(ioDispatcher) {
            daoFav.getAllMovies()
        }


    suspend fun getAllFavSeries() =
        withContext(ioDispatcher) {
            daoFav.getAllSeries()
        }


    suspend fun getFavMovieById(movieId: Int) =
        withContext(ioDispatcher) {
            daoFav.getMovieById(movieId)
        }


    suspend fun getFavSeriesById(seriesId: Int) =
        withContext(ioDispatcher) {
            daoFav.getSeriesById(seriesId)
        }


    suspend fun isMovieFavorite(movieId: Int) =
        withContext(ioDispatcher) {
            daoFav.favoriteMovieExists(movieId)
        }


    suspend fun isSeriesFavorite(seriesId: Int) =
        withContext(ioDispatcher) {
            daoFav.favoriteSeriesExists(seriesId)
        }


    suspend fun deleteFavMovie(toDelete: MovieEntity) =
        withContext(ioDispatcher) {
            daoFav.deleteMovie(toDelete)
        }


    suspend fun deleteFavSeries(toDelete: SeriesEntity) =
        withContext(ioDispatcher) {
            daoFav.deleteSeries(toDelete)
        }

}