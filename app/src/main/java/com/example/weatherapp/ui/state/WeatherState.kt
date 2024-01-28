package com.example.weatherapp.ui.state

import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.domain.model.HourlyWeatherModel


sealed class WeatherState {
    object Loading : WeatherState()
    sealed class Success : WeatherState(){
        data class CurrentWeatherSuccess(val data: CurrentWeatherModel) : Success()
        data class HourlyWeatherSuccess(val data: HourlyWeatherModel) : Success()
        data class DailyWeatherSuccess(val data: DailyWeatherModel) : Success()
    }

    data class Error(val message: String) : WeatherState()
}
