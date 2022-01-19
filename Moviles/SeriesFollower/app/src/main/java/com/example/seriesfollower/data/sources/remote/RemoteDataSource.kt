package com.example.seriesfollower.data.sources.remote

import com.example.seriesfollower.data.models.apimodels.BaseApiResponse
import com.example.seriesfollower.data.models.apimodels.movie.ApiMovie
import com.example.seriesfollower.data.models.apimodels.query.ApiQuery
import com.example.seriesfollower.data.models.apimodels.series.general.ApiSeries
import com.example.seriesfollower.data.models.apimodels.toOwnModel
import com.example.seriesfollower.data.models.apimodels.toOwnQuery
import com.example.seriesfollower.data.models.apimodels.toOwnSeries
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val moviesService: TMDBService) :
    BaseApiResponse() {
    suspend fun getMovieById(movieId: Int) = safeApiCall(
        apiCall = { moviesService.getMovieById(movieId) },
        transform = ApiMovie::toOwnModel
    )

    suspend fun getMultiByQuery(queryTerm: String, pageNum: Int) = safeApiCall(
        apiCall = { moviesService.getMultiByQuery(queryTerm, pageNum) },
        transform = ApiQuery::toOwnQuery
    )

    suspend fun getSeriesById(seriesId: Int) = safeApiCall(
        apiCall = { moviesService.getSeriesById(seriesId) },
        transform = ApiSeries::toOwnSeries
    )

    suspend fun getTrendingResults(page: Int) = safeApiCall(
        apiCall = { moviesService.getTrendingResults(page) },
        transform = ApiQuery::toOwnQuery
    )
}