package com.example.seriesfollower.data.sources.remote

import com.example.seriesfollower.data.model.movie.FullMovies
import com.example.seriesfollower.data.model.query.QueryResult
import com.example.seriesfollower.data.model.series.episode.SeriesEpisodeFull
import com.example.seriesfollower.data.model.series.general.SeriesFull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET(ApiConsts.GET_MOVIE_ID)
    suspend fun getMovieById(@Path(ApiConsts.PATH_PARAM_MOVIE_ID) movieId: Int): Response<FullMovies>

    @GET(ApiConsts.GET_MULTI_QUERY)
    suspend fun getMultiByQuery(@Query(ApiConsts.QUERY_PARAM_QUERY) searchTerm:String, @Query(ApiConsts.QUERY_PARAM_PAGE) pageNum:Int): Response<QueryResult>

    @GET(ApiConsts.GET_SERIES_ID)
    suspend fun getSeriesById(@Path(ApiConsts.PATH_PARAM_SERIES_ID) seriesId:Int): Response<SeriesFull>

    @GET(ApiConsts.GET_SERIES_EPISODE_ID)
    suspend fun getEpisodeById(@Path(ApiConsts.PATH_PARAM_SERIES_ID) seriesId: Int, @Path(ApiConsts.PATH_PARAM_SERIES_SEASON_ID) numSeason: Int, @Path(ApiConsts.PATH_PARAM_SERIES_EPISODE_ID) numEpisode:Int): Response<SeriesEpisodeFull>

    @GET(ApiConsts.GET_MULTI_TRENDING)
    suspend fun getTrendingResults(@Query(ApiConsts.QUERY_PARAM_PAGE)page: Int): Response<QueryResult>
}