package com.example.seriesfollower.usecases

import com.example.seriesfollower.data.repositories.MoviesSeriesRepository
import javax.inject.Inject

class GetTrendingResults @Inject constructor(private val repository: MoviesSeriesRepository){
    suspend fun invoke(page:Int) = repository.getTrendingResults(page)
}