package com.example.seriesfollower.data.models.localmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.seriesfollower.domain.model.queryresult.ResultType

@Entity(tableName = "TRENDING")
data class TrendingResultsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "TRENDING_ID")
    val id: Int,
    @ColumnInfo(name = "TITLE")
    val title: String,
    @ColumnInfo(name = "POPULARITY")
    val popularity: Double,
    @ColumnInfo(name = "VOTE_AVERAGE")
    val voteAverage: Double,
    @ColumnInfo(name = "RESULT_TYPE")
    val resultType: ResultType,
    @ColumnInfo(name = "MAIN_IMAGE")
    val mainImage: String,
)