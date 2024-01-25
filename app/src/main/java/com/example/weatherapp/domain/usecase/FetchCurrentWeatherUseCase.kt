package com.example.weatherapp.domain.usecase


import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke (latitude: Double, longitude: Double) : CurrentWeatherModel {
        val response = weatherRepository.getCurrentWeather(latitude, longitude)
        return CurrentWeatherModel (
            time = response.current.time,
            temperature = response.current.temperature2m,
            apparentTemperature = response.current.apparentTemperature,
            weatherCode = response.current.weatherCode
        )
    }
}