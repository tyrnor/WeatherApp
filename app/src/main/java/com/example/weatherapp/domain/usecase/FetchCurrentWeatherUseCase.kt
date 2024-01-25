package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke (latitude: Double, longitude: Double) = weatherRepository.getCurrentWeather(latitude, longitude)
}