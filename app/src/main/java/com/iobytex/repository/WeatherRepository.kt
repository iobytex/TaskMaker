package com.iobytex.repository

import com.iobytex.domain.Result
import com.iobytex.domain.Weather
import com.iobytex.network.WeatherClient
import com.iobytex.persistence.WeatherDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val viewModelScope: CoroutineScope,private val weatherClient: WeatherClient,private val weatherDao: WeatherDao,private val ioDispatcher: CoroutineDispatcher) {

    suspend fun fetchCurrentWeather(lat:Double,lon:Double){
        withContext(ioDispatcher){
            while (true){
                delay(5000)
                val weatherResponse = weatherClient.fetchCurrentWeather(lat=lat,lon = lon)
                weatherResponse.weather?.get(0)?.let {
                    weatherDao.insertCurrentWeather(weather = it)
                }
            }
        }
    }

    fun  loadCurrentWeather(): Flow<Result<Weather>> = weatherDao.loadCurrentWeather().map { weather ->
        Result.Success(weather)
    }


}