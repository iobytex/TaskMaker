package com.iobytex.network

import com.iobytex.domain.WeatherResponse
import com.iobytex.task.BuildConfig
import com.iobytex.utils.Utils
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherClient @Inject constructor(private val weatherService: WeatherService) {
    suspend fun fetchCurrentWeather(lat:Double,lon:Double,apiKey:String = BuildConfig.WEATHER_API_KEY) : WeatherResponse{
        while (true){
            delay(10000)
            return weatherService.getCurrentWeather(lat,lon,apiKey)
        }
    }
}