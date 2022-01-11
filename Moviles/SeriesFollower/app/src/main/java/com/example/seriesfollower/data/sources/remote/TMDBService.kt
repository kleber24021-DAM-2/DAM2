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
    @GET("/movie/{movieId}?append_to_response=videos,images")
    suspend fun getMovieById(@Path("movieId") movieId: Int): Response<FullMovies>

    @GET("/search/multi")
    suspend fun getMultiByQuery(@Query("query") searchTerm:String, @Query("page") pageNum:Int): Response<QueryResult>

    @GET("/tv/{seriesId}?append_to_response=videos,images")
    suspend fun getSeriesById(@Path("seriesId") seriesId:Int): Response<SeriesFull>

    @GET("/tv/{seriesId}/season/{numSeason}/episode/{numEpisode}?append_to_response=videos,images")
    suspend fun getEpisodeById(@Path("seriesId") seriesId: Int, @Path("numSeason") numSeason: Int, @Path("numEpisode") numEpisode:Int): Response<SeriesEpisodeFull>
}