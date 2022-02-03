package com.example.seriesfollower.ui.search

import com.example.seriesfollower.domain.model.queryresult.QueryInfo

interface SearchContract {
    sealed class Event {
        data class GetSearchResults(val queryTerm: String, val pageNum: Int) :
            SearchContract.Event()

        object ErrorMostrado : SearchContract.Event()
    }

    data class State(
        val queryInfo: QueryInfo? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}