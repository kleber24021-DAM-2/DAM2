package com.example.seriesfollower.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seriesfollower.data.models.localmodels.MovieEntity
import com.example.seriesfollower.data.models.localmodels.SeriesEntity
import com.example.seriesfollower.data.models.localmodels.TrendingResultsEntity

@Database(
    entities = [MovieEntity::class, SeriesEntity::class, TrendingResultsEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}