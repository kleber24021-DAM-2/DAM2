package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.models.localmodels.SeriesEntity
import com.example.seriesfollower.data.sources.local.FavoritesDao
import com.example.seriesfollower.data.sources.remote.RemoteDataSource
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.series.episode.OwnEpisode
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import com.example.seriesfollower.domain.model.series.season.OwnSeason
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    fun getOnlineSeriesById(seriesId: Int) : Flow<NetworkResult<OwnSeries>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getSeriesById(seriesId)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    suspend fun insertFavoriteSeries(toInsert: SeriesEntity) =
        withContext(ioDispatcher) {
            daoFav.insertFavoriteSeries(toInsert)
        }

    suspend fun getAllFavSeries() = withContext(ioDispatcher){
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

    fun getSeasonEpisodes(seriesId: Int, seasonNum:Int) : Flow<NetworkResult<List<OwnEpisode>>>{
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getSeasonEpisodes(seriesId, seasonNum)
            emit(result)
        }.flowOn(ioDispatcher)
    }
}