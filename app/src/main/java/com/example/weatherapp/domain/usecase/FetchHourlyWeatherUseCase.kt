package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.exceptions.CustomException
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.HourlyWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import java.io.IOException
import javax.inject.Inject

class FetchHourlyWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double, startHour: String, endHour: String ) : Result<HourlyWeatherModel> {
        return try {
            val response = weatherRepository.getHourlyWeather(latitude, longitude, startHour, endHour)
            Result.success(HourlyWeatherModel(
                time = response.hourly.time,
                temperature = response.hourly.temperature2m,
                apparentTemperature = response.hourly.apparentTemperature,
                weatherCode = response.hourly.weatherCode
            ))
        } catch (e: IOException){
            Result.failure(CustomException("Network Failure : Unable to reach the server. Check your Network connection"))
        } catch (e: Exception) {
            Result.failure(CustomException("An unexpected error occurred"))
        }
    }
}