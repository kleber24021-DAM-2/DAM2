package com.example.seriesfollower.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.model.toQueryInfo
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.queryresult.QueryInfo
import com.example.seriesfollower.ui.UserMessages
import com.example.seriesfollower.usecases.GetTrendingResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val getTrendingResults: GetTrendingResults) :
    ViewModel() {
    private val _results = MutableLiveData<QueryInfo>()
    val results: LiveData<QueryInfo> get() = _results

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getTrendingResults(page: Int) {
        viewModelScope.launch {
            val result = getTrendingResults.invoke(page)

            when (result) {
                is NetworkResult.Error -> _error.postValue(
                    result.message ?: UserMessages.UNEXPECTED_DB_ERROR
                )
                is NetworkResult.Success -> _results.postValue(
                    result.data!!.toQueryInfo()
                )
                is NetworkResult.Loading -> _error.postValue(result.message?:"Loading")
            }
        }
    }
}
