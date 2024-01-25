package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.HourlyWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchHourlyWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double, startHour: String, endHour: String ) : HourlyWeatherModel {
        val response = weatherRepository.getHourlyWeather(latitude, longitude, startHour, endHour)
        return HourlyWeatherModel(
            time = response.hourly.time,
            temperature = response.hourly.temperature2m,
            apparentTemperature = response.hourly.apparentTemperature,
            weatherCode = response.hourly.weatherCode
        )
    }
}