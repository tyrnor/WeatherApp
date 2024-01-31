package com.example.weatherapp.ui.utils

import com.example.weatherapp.R

object BackgroundSelector {
    fun select (code: Int, isDay: Int) : Int {
        if (isDay == 1) {
            return when (code) {
                0 -> R.drawable.day_clear_sky_bg
                1 -> R.drawable.day_mostly_clear_bg
                2 -> R.drawable.day_partly_cloudy_bg
                3 -> R.drawable.day_cloudy_bg
                else -> R.drawable.day_clear_sky_bg
            }
        } else {
            return when (code) {
                0 -> R.drawable.night_clear_sky_bg
                1 -> R.drawable.night_mostly_clear_bg
                2 -> R.drawable.night_partly_cloudy_bg
                3 -> R.drawable.night_cloudy_bg
                else -> R.drawable.night_clear_sky_bg
            }

        }
    }
}