package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.exceptions.CustomException
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import java.io.IOException
import javax.inject.Inject

class FetchDailyWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<DailyWeatherModel> {
        return try {
            val response = weatherRepository.getDailyWeather(latitude, longitude)
            Result.success(
                DailyWeatherModel(
                    time = response.daily.time,
                    maxTemperature = response.daily.temperature2mMax,
                    minTemperature = response.daily.temperature2mMin,
                    apparentMaxTemperature = response.daily.apparentTemperatureMax,
                    apparentMinTemperature = response.daily.apparentTemperatureMin,
                    weatherCode = response.daily.weatherCode
                )
            )
        } catch (e: IOException) {
            Result.failure(CustomException("Network Failure : Unable to reach the server. Check your Network connection"))
        } catch (e: Exception) {
            Result.failure(CustomException("An unexpected error occurred"))
        }
    }
}