package com.example.seriesfollower.data.sources.remote

import com.example.seriesfollower.data.model.BaseApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val moviesService:TMDBService) : BaseApiResponse(){
    suspend fun getMovieById(movieId:Int) = safeApiCall { moviesService.getMovieById(movieId) }
    suspend fun getMultiByQuery(queryTerm:String, pageNum:Int) = safeApiCall { moviesService.getMultiByQuery(queryTerm, pageNum) }
    suspend fun getSeriesById(seriesId:Int) = safeApiCall { moviesService.getSeriesById(seriesId) }
    suspend fun getEpisodeById(seriesId: Int, numSeason: Int, numEpisode:Int) = safeApiCall { moviesService.getEpisodeById(seriesId, numSeason, numEpisode) }
    suspend fun getTrendingResults(page:Int) = safeApiCall { moviesService.getTrendingResults(page) }
}