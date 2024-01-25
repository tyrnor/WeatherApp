package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchHourlyWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double, startHour: String, endHour: String ) = weatherRepository.getHourlyWeather(latitude, longitude, startHour, endHour)
}