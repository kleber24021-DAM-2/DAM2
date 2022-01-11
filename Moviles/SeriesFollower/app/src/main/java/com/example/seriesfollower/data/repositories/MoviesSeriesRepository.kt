package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
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
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMovieById(movieId:Int){
        withContext(ioDispatcher){
            remoteDataSource.getMovieById(movieId)
        }
    }

    suspend fun getMultiByQuery(queryTerm:String, pageNum:Int){
        withContext(ioDispatcher){
            remoteDataSource.getMultiByQuery(queryTerm, pageNum)
        }
    }

    suspend fun getSeriesById(seriesId:Int){
        withContext(ioDispatcher){
            remoteDataSource.getSeriesById(seriesId)
        }
    }

    suspend fun getEpisodeById(seriesId:Int, seasonNum:Int, episodeNum:Int){
        withContext(ioDispatcher){
            remoteDataSource.getEpisodeById(seriesId,seasonNum, episodeNum)
        }
    }
}