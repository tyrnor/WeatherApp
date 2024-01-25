package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchDailyWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke (latitude: Double, longitude: Double) : DailyWeatherModel {
        val response = weatherRepository.getDailyWeather(latitude, longitude)
        return DailyWeatherModel(
            time = response.daily.time,
            maxTemperature = response.daily.temperature2mMax,
            minTemperature = response.daily.temperature2mMin,
            apparentMaxTemperature = response.daily.apparentTemperatureMax,
            apparentMinTemperature = response.daily.apparentTemperatureMin,
            weatherCode = response.daily.weatherCode
        )
    }
}