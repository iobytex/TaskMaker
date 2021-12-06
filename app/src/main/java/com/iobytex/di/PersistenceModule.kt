package com.iobytex.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iobytex.persistence.AppDatabase
import com.iobytex.persistence.TaskDao
import com.iobytex.persistence.WeatherDao
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase):TaskDao{
        return appDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase):WeatherDao{
        return appDatabase.weatherDao()
    }
}