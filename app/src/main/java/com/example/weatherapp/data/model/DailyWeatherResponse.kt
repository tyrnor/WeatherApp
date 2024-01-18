package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class DailyWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: DailyWeather
)

data class DailyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m_max") val temperature2mMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperature2mMin: List<Double>,
    @SerializedName("weather_code") val weatherCode: List<Int>
)
