package com.iobytex.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iobytex.domain.Task
import com.iobytex.domain.Weather

@Database(entities = [Task::class,Weather::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase(){
    abstract fun taskDao() : TaskDao
    abstract fun weatherDao() : WeatherDao

    companion object{
        const val DB_NAME = "Maker.db"
    }
}