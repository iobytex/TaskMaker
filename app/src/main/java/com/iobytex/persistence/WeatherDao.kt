package com.iobytex.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.iobytex.domain.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun insertCurrentWeather(weather: Weather)

    @Query("SELECT * FROM weather ORDER BY createdAt ASC LIMIT 1")
    fun loadCurrentWeather() : Flow<Weather>
}