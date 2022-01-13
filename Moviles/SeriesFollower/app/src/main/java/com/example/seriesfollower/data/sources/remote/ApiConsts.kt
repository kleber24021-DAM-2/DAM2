package com.example.seriesfollower.data.sources.remote

object ApiConsts {
    const val GET_MOVIE_ID = "movie/{movieId}?append_to_response=videos,images"
    const val GET_MULTI_QUERY = "search/multi"
    const val GET_SERIES_ID = "tv/{seriesId}?append_to_response=videos,images"
    const val GET_SERIES_EPISODE_ID = "tv/{seriesId}/season/{numSeason}/episode/{numEpisode}?append_to_response=videos,images"
    const val GET_MULTI_TRENDING = "trending/all/week"
    const val PATH_PARAM_MOVIE_ID = "movieId"
    const val QUERY_PARAM_QUERY = "query"
    const val QUERY_PARAM_PAGE = "page"
    const val PATH_PARAM_SERIES_ID = "seriesId"
    const val PATH_PARAM_SERIES_SEASON_ID = "numSeason"
    const val PATH_PARAM_SERIES_EPISODE_ID = "numEpisode"

}
