package com.example.seriesfollower.data.sources.local.di

import android.content.Context
import androidx.room.Room
import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.sources.local.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        FavoritesDatabase::class.java,
        DataConsts.DATABASE_NAME
    )
        .fallbackToDestructiveMigrationFrom(1)
        .build()

    @Provides
    fun providesFavoritesDao(database: FavoritesDatabase) = database.favoritesDao()
}