package com.example.seriesfollower.data.sources.remote.di

import com.example.seriesfollower.data.DataConsts
import com.example.seriesfollower.data.sources.remote.ServiceInterceptor
import com.example.seriesfollower.data.sources.remote.TMDBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideServiceInterceptor() : ServiceInterceptor = ServiceInterceptor()

    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient{
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideTMDBService(retrofit: Retrofit): TMDBService =
        retrofit.create(TMDBService::class.java)

    @Named(DataConsts.IO_DISPATCHER)
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}