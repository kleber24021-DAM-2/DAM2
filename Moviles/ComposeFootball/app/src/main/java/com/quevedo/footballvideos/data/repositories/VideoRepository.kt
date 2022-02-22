package com.quevedo.footballvideos.data.repositories

import com.quevedo.footballvideos.data.models.NetworkResult
import com.quevedo.footballvideos.data.sources.remote.DataConsts
import com.quevedo.footballvideos.data.sources.remote.RemoteDataSource
import com.quevedo.footballvideos.domain.models.OwnFootballVideo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class VideoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
){
    fun getAllVideos() : Flow<NetworkResult<List<OwnFootballVideo>>>{
        return flow {
            val result = remoteDataSource.getAllFootballVideos()
            emit(result)
        }.flowOn(ioDispatcher)
    }
}