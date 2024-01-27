package com.example.weatherapp.ui.utils

import java.time.format.DateTimeFormatter

object Formatters {
    val formatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH")
}