package com.example.weatherapp.domain.model

data class HourlyWeatherInfo(
    val hour: Int,
    val description: String,
    val temperature: Int
)
