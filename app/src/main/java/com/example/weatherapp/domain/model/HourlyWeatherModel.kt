package com.example.weatherapp.domain.model

import com.example.weatherapp.ui.utils.WeatherCodeTranslator
import kotlin.math.roundToInt

data class HourlyWeatherModel (
    val time: List<String>,
    val temperature: List<Double>,
    val apparentTemperature: List<Double>,
    val weatherCode: List<Int>
) {
    private val icons : List<Int>
        get() = weatherCode.map { weatherCode -> WeatherCodeTranslator.convertToIcon(weatherCode) }
    fun getCombinedWeatherInfo(): List<HourlyWeatherInfo> {
        return time.zip(temperature) { time, temp -> Pair(time, temp) }
            .zip(icons) { (time, temp), icon -> Triple(time, temp, icon) }
            .map { (time, temp, icon) ->
                val hour = if (time.length >= 13) time.substring(11, 13).toInt() else 0
                val roundedTemp = temp.roundToInt()
                HourlyWeatherInfo(hour, icon, roundedTemp)
            }
    }
}

