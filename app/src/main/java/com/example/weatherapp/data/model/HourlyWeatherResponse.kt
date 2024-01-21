package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class HourlyWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val hourly: HourlyWeather
)

data class HourlyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperature2m: List<Double>,
    @SerializedName("apparent_temperature") val apparentTemperature: List<Double>,
    @SerializedName("weather_code") val weatherCode: List<Int>
)
