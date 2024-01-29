package com.example.weatherapp.domain.model

import com.example.weatherapp.ui.utils.WeatherCodeTranslator
import kotlin.math.roundToInt

data class HourlyWeatherModel (
    val time: List<String>,
    val temperature: List<Double>,
    val apparentTemperature: List<Double>,
    val weatherCode: List<Int>
) {
    private val descriptions: List<String>
        get() = weatherCode.map { code -> WeatherCodeTranslator.translate(code) }
    fun getCombinedWeatherInfo(): List<HourlyWeatherInfo> {
        return time.zip(temperature) { time, temp -> Pair(time, temp) }
            .zip(descriptions) { (time, temp), desc -> Triple(time, temp, desc) }
            .map { (time, temp, desc) ->
                val hour = if (time.length >= 13) time.substring(11, 13).toInt() else 0
                val roundedTemp = temp.roundToInt()
                HourlyWeatherInfo(hour, desc, roundedTemp)
            }
    }
}

