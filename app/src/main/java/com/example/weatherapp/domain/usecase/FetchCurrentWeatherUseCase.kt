package com.example.weatherapp.domain.usecase


import com.example.weatherapp.domain.exceptions.CustomException
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.repository.WeatherRepository
import java.io.IOException
import javax.inject.Inject

class FetchCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<CurrentWeatherModel> {

        return try {
            val response = weatherRepository.getCurrentWeather(latitude, longitude)
            Result.success(
                CurrentWeatherModel(
                    time = response.current.time,
                    temperature = response.current.temperature2m,
                    apparentTemperature = response.current.apparentTemperature,
                    weatherCode = response.current.weatherCode
                )
            )
        } catch (e: IOException) {
            Result.failure(CustomException("Network Failure : Unable to reach the server. Check your Network connection"))
        } catch (e: Exception) {
            Result.failure(CustomException("An unexpected error occurred"))
        }

    }
}

