package com.example.seriesfollower.domain.usecases

import com.example.seriesfollower.data.repositories.MoviesRepository
import com.example.seriesfollower.data.repositories.MultiResultRepository
import javax.inject.Inject

class GetResultsByQuery @Inject constructor(private val multiRepository: MultiResultRepository) {
    suspend fun invoke(queryTerm: String, pageNum: Int) =
        multiRepository.getMultiByQuery(queryTerm, pageNum)
}