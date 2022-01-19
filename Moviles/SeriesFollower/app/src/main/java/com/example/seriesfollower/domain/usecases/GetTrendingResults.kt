package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.queryresult.QueryInfo
import javax.inject.Inject

class GetTrendingResults @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(page: Int): NetworkResult<QueryInfo> = repository.getTrendingResults(page)
}

