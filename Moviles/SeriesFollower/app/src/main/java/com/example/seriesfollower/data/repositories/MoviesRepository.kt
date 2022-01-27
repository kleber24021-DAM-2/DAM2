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
class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val daoFav: FavoritesDao
) {
    suspend fun getOnlineMovieById(movieId: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getMovieById(movieId)
        }

    suspend fun insertFavoriteMovie(toInsert: MovieEntity) =
        withContext(ioDispatcher) {
            daoFav.insertFavoriteMovie(toInsert)
        }


    suspend fun getAllFavMovies() =
        withContext(ioDispatcher) {
            daoFav.getAllMovies()
        }


    suspend fun getFavMovieById(movieId: Int) =
        withContext(ioDispatcher) {
            daoFav.getMovieById(movieId)
        }


    suspend fun isMovieFavorite(movieId: Int) =
        withContext(ioDispatcher) {

            daoFav.favoriteMovieExists(movieId)
        }


    suspend fun deleteFavMovie(toDelete: MovieEntity) =
        withContext(ioDispatcher) {
            daoFav.deleteMovie(toDelete)
        }


}