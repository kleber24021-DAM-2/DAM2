package com.quevedo.footballvideos.data.sources.remote

import com.quevedo.footballvideos.data.models.video.FootballVideo
import retrofit2.Response
import retrofit2.http.GET

interface ScorebatService {
    @GET("feed")
    suspend fun getAllVideos():Response<FootballVideo>
}