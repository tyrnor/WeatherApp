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
        return openMeteoService.getCurrentWeather(latitude, longitude, "temperature_2m,weather_code")
    }

    override suspend fun getHourlyWeather(latitude: Double, longitude: Double): HourlyWeatherResponse {
        return openMeteoService.getHourlyWeather(latitude, longitude, "temperature_2m,weather_code")
    }

    override suspend fun getDailyWeather(latitude: Double, longitude: Double): DailyWeatherResponse {
        return openMeteoService.getDailyWeather(latitude, longitude, "weather_code,temperature_2m_max,temperature_2m_min")
    }
}
