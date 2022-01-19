package com.example.seriesfollower.domain.model.queryresult

data class QueryInfo(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<OwnResult>
)