package com.example.weatherapp.domain.model


data class DailyWeatherModel(
    val time: List<String>,
    val maxTemperature: List<Double>,
    val minTemperature: List<Double>,
    val apparentMaxTemperature: List<Double>,
    val apparentMinTemperature: List<Double>,
    val weatherCode: List<Int>
)