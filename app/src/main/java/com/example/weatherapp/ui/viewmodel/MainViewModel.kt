package com.example.weatherapp.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.FetchCurrentWeatherUseCase
import com.example.weatherapp.domain.usecase.FetchDailyWeatherUseCase
import com.example.weatherapp.domain.usecase.FetchHourlyWeatherUseCase
import com.example.weatherapp.ui.utils.Formatters.formatterDay
import com.example.weatherapp.ui.utils.Formatters.formatterTime
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    private val fetchHourlyWeatherUseCase: FetchHourlyWeatherUseCase,
    private val fetchDailyWeatherUseCase: FetchDailyWeatherUseCase
) : AndroidViewModel(application) {
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private lateinit var currentLocation: Task<Location>
    private lateinit var startHour: String
    private lateinit var endHour: String

    fun fetchWeatherData() {
        requestCurrentLocation()
        getCurrentDateTime()
        currentLocation.addOnSuccessListener { location ->
            location?.let {
                viewModelScope.launch {
                    try {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val currentWeather = fetchCurrentWeatherUseCase(latitude, longitude)
                        Log.d("WeatherApp", "Current Weather: $currentWeather")

                        val hourlyWeather = fetchHourlyWeatherUseCase(latitude, longitude, startHour, endHour)
                        Log.d("WeatherApp", "Hourly Weather: $hourlyWeather")

                        val dailyWeather = fetchDailyWeatherUseCase(latitude, longitude)
                        Log.d("WeatherApp", "Daily Weather: $dailyWeather")
                    } catch (e: Exception) {
                        Log.e("WeatherApp", "Error fetching weather data", e)
                    }
                }
            } ?: Log.e("WeatherApp", "Location is null")
        }.addOnFailureListener {
            Log.e("WeatherApp", "Failed to get location", it)
        }
    }
      fun requestCurrentLocation(){
         if (ActivityCompat.checkSelfPermission(
                 getApplication(),
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 getApplication(),
                 Manifest.permission.ACCESS_COARSE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             return
         }
         currentLocation = fusedLocationClient
            .getCurrentLocation(
                CurrentLocationRequest
                    .Builder()
                    .setMaxUpdateAgeMillis(15000)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build(),
                null)
        currentLocation.addOnSuccessListener {
            Log.d("TAG", "requestCurrentLocation: ${it.latitude} ${it.longitude}")
        }
    }
    private fun getCurrentDateTime(){
        val currentDay = LocalDateTime.now().format(formatterDay)
        val nextDay = LocalDateTime.now().plusDays(1).format(formatterDay)
        val currentTime = LocalDateTime.now().format(formatterTime)
        startHour = "${currentDay}T${currentTime}:00"
        endHour = "${nextDay}T${currentTime}:00"
    }
}

