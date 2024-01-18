package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import android.util.Log
import com.example.weatherapp.domain.repository.WeatherRepository

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                val currentWeather = weatherRepository.getCurrentWeather(41.3888, 2.159)
                Log.d("com.example.weatherapp.ui.view.WeatherApp", "Current Weather: $currentWeather")

                val hourlyWeather = weatherRepository.getHourlyWeather(41.3888, 2.159)
                Log.d("com.example.weatherapp.ui.view.WeatherApp", "Hourly Weather: $hourlyWeather")

                val dailyWeather = weatherRepository.getDailyWeather(41.3888, 2.159)
                Log.d("com.example.weatherapp.ui.view.WeatherApp", "Daily Weather: $dailyWeather")
            } catch (e: Exception) {
                Log.e("com.example.weatherapp.ui.view.WeatherApp", "Error fetching weather data", e)
            }
        }
    }
}
