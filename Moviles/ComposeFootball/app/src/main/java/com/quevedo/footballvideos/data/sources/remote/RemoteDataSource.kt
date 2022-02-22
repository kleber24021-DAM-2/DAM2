package com.quevedo.footballvideos.data.sources.remote

import com.quevedo.footballvideos.data.models.BaseApiResponse
import com.quevedo.footballvideos.data.models.toFootballVideo
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val footballService: ScorebatService) :
    BaseApiResponse() {
    suspend fun getAllFootballVideos() = safeApiCall(
        apiCall = { footballService.getAllVideos() },
        transform = { footballVideo ->
            footballVideo.response?.map {
                it.toFootballVideo()
            }?.toList() ?: emptyList()
        }
    )
}