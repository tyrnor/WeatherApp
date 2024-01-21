package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.data.model.DailyWeatherResponse
import com.example.weatherapp.data.model.HourlyWeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherResponse
    suspend fun getHourlyWeather(latitude: Double, longitude: Double, startHour: String, endHour: String): HourlyWeatherResponse
    suspend fun getDailyWeather(latitude: Double, longitude: Double): DailyWeatherResponse
}
