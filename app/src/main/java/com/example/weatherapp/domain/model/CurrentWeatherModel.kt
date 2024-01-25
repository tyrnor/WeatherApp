package com.example.weatherapp.domain.model


data class CurrentWeatherModel(
    val time: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val weatherCode: Int
)