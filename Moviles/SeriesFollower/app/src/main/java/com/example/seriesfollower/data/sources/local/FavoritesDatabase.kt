package com.example.seriesfollower.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seriesfollower.data.models.localmodels.MovieEntity
import com.example.seriesfollower.data.models.localmodels.SeriesEntity

@Database(
    entities = [MovieEntity::class, SeriesEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}