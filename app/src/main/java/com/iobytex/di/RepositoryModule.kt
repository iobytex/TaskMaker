package com.iobytex.di

import com.iobytex.domain.TaskModelAdapter
import com.iobytex.domain.Weather
import com.iobytex.network.WeatherClient
import com.iobytex.persistence.TaskDao
import com.iobytex.persistence.WeatherDao
import com.iobytex.repository.TaskRepository
import com.iobytex.repository.WeatherRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherRepo(viewModelScope: CoroutineScope, weatherClient: WeatherClient, weatherDao: WeatherDao, coroutineDispatcher: CoroutineDispatcher): WeatherRepository{
        return WeatherRepository(viewModelScope,weatherClient,weatherDao,coroutineDispatcher)
    }

    @TaskModelMoshi
    @Provides
    @Singleton
    fun provideTaskMoshi() : Moshi {
        return Moshi.Builder().add(TaskModelAdapter()).build()
    }

    @Provides
    @ViewModelScoped
    fun provideTaskRepo(@TaskModelMoshi moshi: Moshi,taskDao: TaskDao,coroutineDispatcher: CoroutineDispatcher) : TaskRepository{
        return TaskRepository(moshi,taskDao,coroutineDispatcher)
    }


}