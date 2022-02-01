package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.MultiResultRepository
import com.example.seriesfollower.data.repositories.SeriesRepository
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.queryresult.QueryInfo
import javax.inject.Inject

class GetTrendingResults @Inject constructor(private val repository: MultiResultRepository) {
    suspend fun invoke(page: Int) = repository.getTrendingResults(page)
}

