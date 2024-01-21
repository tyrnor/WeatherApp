package com.example.weatherapp.ui.utils

import java.time.format.DateTimeFormatter

object Formatters {
    val formatterDay = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatterTime = DateTimeFormatter.ofPattern("HH")
}