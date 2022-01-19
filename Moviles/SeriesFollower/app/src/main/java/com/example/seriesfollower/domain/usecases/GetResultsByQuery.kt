package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import javax.inject.Inject

class GetResultsByQuery @Inject constructor(private val repository: MoviesSeriesRepository) {
    suspend fun invoke(queryTerm: String, pageNum: Int) =
        repository.getMultiByQuery(queryTerm, pageNum)
}