package com.example.weatherapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.domain.model.CurrentWeatherModel
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherView(
    weather: CurrentWeatherModel,
    cityName: String,
    minTemp: Int,
    maxTemp: Int,
) {
    val roundedTemp = weather.temperature.roundToInt()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "My location", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )
        Text(
            text = cityName.uppercase(), style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )
        Text(
            text = "${roundedTemp}°", style = TextStyle(
                fontSize = 72.sp,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )
        Text(
            text = weather.description, style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )
        Text(
            text = "Max. ${maxTemp}°  Min. ${minTemp}°", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )

    }
}

@Composable

fun CurrentLoadingView(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "My location", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            ), color = Color.Transparent
        )
        Text(
            text = "CityName", style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            ), color = Color.Transparent
        )
        Text(
            text = "00", style = TextStyle(
                fontSize = 72.sp,
            ), color = Color.Transparent
        )
        Text(
            text = "Description", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
            ), color = Color.Transparent
        )
        Text(
            text = "Max. 00°  Min. 00°", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
            ), color = Color.Transparent
        )

    }
}