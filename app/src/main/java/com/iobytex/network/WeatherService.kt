package com.iobytex.network

import com.iobytex.domain.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/weather")
    suspend fun getCurrentWeather(@Query("lat")  latitude: Double,@Query("lon")  longitude: Double,@Query("appid")  appId: String) : WeatherResponse

}