package com.example.roomproject.data.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.roomproject.data.Constants
import com.example.roomproject.data.SuperHeroRoomDatabase
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
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context.applicationContext,
        SuperHeroRoomDatabase::class.java,
        Constants.databaseName
    )
        .createFromAsset(Constants.databasePath)
        .fallbackToDestructiveMigrationFrom(5)
        .build()

    @Provides
    fun providesHeroDao(articlesDatabase: SuperHeroRoomDatabase) =
        articlesDatabase.superHeroDao()
}