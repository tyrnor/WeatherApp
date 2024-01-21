package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.data.model.DailyWeatherResponse
import com.example.weatherapp.data.model.HourlyWeatherResponse
import com.example.weatherapp.data.source.remote.OpenMeteoService
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val openMeteoService: OpenMeteoService) :
    WeatherRepository {

    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeatherResponse {
        return openMeteoService.getCurrentWeather(latitude, longitude, current = "temperature_2m,apparent_temperature,weather_code")
    }

    override suspend fun getHourlyWeather(latitude: Double, longitude: Double, startHour: String, endHour: String): HourlyWeatherResponse {
        return openMeteoService.getHourlyWeather(latitude, longitude, hourly = "temperature_2m,apparent_temperature,weather_code", startHour = startHour, endHour = endHour)
    }

    override suspend fun getDailyWeather(latitude: Double, longitude: Double): DailyWeatherResponse {
        return openMeteoService.getDailyWeather(latitude, longitude, daily = "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min")
    }
}
