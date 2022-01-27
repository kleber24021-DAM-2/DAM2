package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.models.localmodels.SeriesEntity
import com.example.seriesfollower.data.sources.local.FavoritesDao
import com.example.seriesfollower.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class SeriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val daoFav: FavoritesDao
) {
    suspend fun getOnlineSeriesById(seriesId: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getSeriesById(seriesId)

        }

    suspend fun insertFavoriteSeries(toInsert: SeriesEntity) =
        withContext(ioDispatcher) {
            daoFav.insertFavoriteSeries(toInsert)
        }

    suspend fun getAllFavSeries() =
        withContext(ioDispatcher) {
            daoFav.getAllSeries()
        }

    suspend fun getFavSeriesById(seriesId: Int) =
        withContext(ioDispatcher) {
            daoFav.getSeriesById(seriesId)
        }

    suspend fun isSeriesFavorite(seriesId: Int) =
        withContext(ioDispatcher) {
            daoFav.favoriteSeriesExists(seriesId)
        }

    suspend fun deleteFavSeries(toDelete: SeriesEntity) =
        withContext(ioDispatcher) {
            daoFav.deleteSeries(toDelete)
        }
}