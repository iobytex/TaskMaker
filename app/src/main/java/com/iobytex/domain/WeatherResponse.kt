package com.iobytex.domain

data class WeatherResponse(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int?,
    val coord: Coord?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    val sys: Sys?,
    val timezone: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
) {
    data class Clouds(
        val all: Int?
    )

    data class Coord(
        val lat: Int?,
        val lon: Int?
    )

    data class Main(
        val feels_like: Double?,
        val humidity: Int?,
        val pressure: Int?,
        val temp: Double?,
        val temp_max: Double?,
        val temp_min: Double?
    )

    data class Sys(
        val country: String?,
        val id: Int?,
        val message: Double?,
        val sunrise: Int?,
        val sunset: Int?,
        val type: Int?
    )


    data class Wind(
        val deg: Double?,
        val speed: Double?
    )
}