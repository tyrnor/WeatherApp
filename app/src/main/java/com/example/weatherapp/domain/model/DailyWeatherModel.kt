package com.example.weatherapp.domain.model

import com.example.weatherapp.ui.utils.Formatters.formatterDay
import com.example.weatherapp.ui.utils.WeatherCodeTranslator
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


data class DailyWeatherModel(
    val time: List<String>,
    val maxTemperature: List<Double>,
    val minTemperature: List<Double>,
    val apparentMaxTemperature: List<Double>,
    val apparentMinTemperature: List<Double>,
    val weatherCode: List<Int>,
) {
    val dayOfWeek: List<String>
        get() = time.map { time -> getDayOfWeek(time).substring(0, 3) }
    val icons : List<Int>
        get() = weatherCode.map { weatherCode -> WeatherCodeTranslator.convertToIcon(weatherCode) }

    private fun getDayOfWeek(dateString: String): String {
        val date = LocalDate.parse(dateString, formatterDay)
        return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }
}