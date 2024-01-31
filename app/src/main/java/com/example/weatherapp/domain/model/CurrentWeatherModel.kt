package com.example.weatherapp.domain.model

import com.example.weatherapp.ui.utils.WeatherCodeTranslator


data class CurrentWeatherModel(
    val time: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val isDay: Int,
    val weatherCode: Int
) {
    val description : String
        get() = WeatherCodeTranslator.translate(weatherCode)
}