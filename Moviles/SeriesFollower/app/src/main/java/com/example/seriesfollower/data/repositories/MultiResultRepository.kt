package com.example.seriesfollower.data.repositories

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.sources.local.FavoritesDao
import com.example.seriesfollower.data.sources.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MultiResultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val daoFav: FavoritesDao
){
    suspend fun getMultiByQuery(queryTerm: String, pageNum: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getMultiByQuery(queryTerm, pageNum)

        }
    suspend fun getTrendingResults(page: Int) =
        withContext(ioDispatcher) {
            remoteDataSource.getTrendingResults(page)
        }
}