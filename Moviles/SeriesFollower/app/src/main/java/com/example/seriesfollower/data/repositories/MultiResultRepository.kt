package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.sources.local.FavoritesDao
import com.example.seriesfollower.data.sources.remote.RemoteDataSource
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.queryresult.QueryInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class MultiResultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val daoFav: FavoritesDao
) {
    fun getMultiByQuery(queryTerm: String, pageNum: Int): Flow<NetworkResult<QueryInfo>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getMultiByQuery(queryTerm, pageNum)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    fun getTrendingResults(page: Int): Flow<NetworkResult<QueryInfo>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getTrendingResults(page)
            if (result is NetworkResult.Success) {
                //TODO cacheo
            }
            emit(result)
        }.flowOn(ioDispatcher)
    }
}