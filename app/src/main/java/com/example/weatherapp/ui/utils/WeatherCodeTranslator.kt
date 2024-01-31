package com.example.weatherapp.ui.utils

import com.example.weatherapp.R

object WeatherCodeTranslator {
    fun translate(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1 -> "Mostly clear"
            2 -> "Partly cloudy"
            3 -> "Cloudy"
            in 45..48 -> "Fog"
            in 51..55 -> "Drizzle"
            in 56..57 -> "Freezing Drizzle"
            in 61..65 -> "Rain"
            in 66..67 -> "Freezing Rain"
            in 71..75 -> "Snowfall"
            77 -> "Snow grains"
            in 80..82 -> "Rain showers"
            in 85..86 -> "Snow showers"
            95 -> "Thunderstorm"
            in 96..99 -> "Thunderstorm with hail"
            else -> "Unknown Weather"
        }
    }
    fun convertToIcon(code: Int) : Int {
        return when (code) {
            0 -> R.drawable.clear_sky_ic
            in 1..3 -> R.drawable.cloudy_ic
            else -> R.drawable.clear_sky_ic
        }
    }
}