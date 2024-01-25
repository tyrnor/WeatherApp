package com.example.weatherapp.domain.model

data class HourlyWeatherModel (
    val time: List<String>,
    val temperature: List<Double>,
    val apparentTemperature: List<Double>,
    val weatherCode: List<Int>
)