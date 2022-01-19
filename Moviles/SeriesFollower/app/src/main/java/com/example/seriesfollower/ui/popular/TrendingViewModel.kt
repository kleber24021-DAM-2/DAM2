package com.example.seriesfollower.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.queryresult.QueryInfo
import com.example.seriesfollower.domain.usecases.GetTrendingResults
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingResults: GetTrendingResults
) :
    ViewModel() {
    private val _results = MutableLiveData<QueryInfo>()
    val results: LiveData<QueryInfo> get() = _results

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getTrendingResults(page: Int) {
        viewModelScope.launch {

            when (val result = getTrendingResults.invoke(page)) {
                is NetworkResult.Error -> _error.postValue(
                    result.message ?: UserMessages.UNEXPECTED_DB_ERROR
                )
                is NetworkResult.Success -> result.data.let { queryInfo ->
                    if (queryInfo == null) {
                        _error.postValue(UserMessages.UNEXPECTED_DB_ERROR)
                    } else {
                        _results.postValue(queryInfo)
                    }
                }
                is NetworkResult.Loading -> _error.postValue(result.message ?: UserMessages.LOADING)
            }
        }
    }
}
