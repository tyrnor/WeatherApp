package com.example.weatherapp.data.source.remote

import com.example.weatherapp.data.model.CurrentWeatherResponse
import com.example.weatherapp.data.model.DailyWeatherResponse
import com.example.weatherapp.data.model.HourlyWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {
    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String
    ): CurrentWeatherResponse

    @GET("forecast")
    suspend fun getHourlyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String
    ): HourlyWeatherResponse

    @GET("forecast")
    suspend fun getDailyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String
    ): DailyWeatherResponse
}
