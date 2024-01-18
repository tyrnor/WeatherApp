package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather
)

data class CurrentWeather(
    val time: String,
    @SerializedName("temperature_2m") val temperature2m: Double,
    @SerializedName("weather_code") val weatherCode: Int
)
