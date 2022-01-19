package com.example.seriesfollower.data.sources.remote

import com.example.seriesfollower.data.models.apimodels.movie.ApiMovie
import com.example.seriesfollower.data.models.apimodels.query.ApiQuery
import com.example.seriesfollower.data.models.apimodels.series.general.ApiSeries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET(ApiConsts.GET_MOVIE_ID)
    suspend fun getMovieById(@Path(ApiConsts.PATH_PARAM_MOVIE_ID) movieId: Int): Response<ApiMovie>

    @GET(ApiConsts.GET_MULTI_QUERY)
    suspend fun getMultiByQuery(
        @Query(ApiConsts.QUERY_PARAM_QUERY) searchTerm: String,
        @Query(ApiConsts.QUERY_PARAM_PAGE) pageNum: Int
    ): Response<ApiQuery>

    @GET(ApiConsts.GET_SERIES_ID)
    suspend fun getSeriesById(@Path(ApiConsts.PATH_PARAM_SERIES_ID) seriesId: Int): Response<ApiSeries>

    @GET(ApiConsts.GET_MULTI_TRENDING)
    suspend fun getTrendingResults(@Query(ApiConsts.QUERY_PARAM_PAGE) page: Int): Response<ApiQuery>
}