package com.example.weatherapp.ui.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.ui.theme.AppTypography.appTypography
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
            text = "My location", style = appTypography.titleLargeShadow, color = Color.White
        )
        Text(
            text = cityName.uppercase(), style = appTypography.bodySmallShadow, color = Color.White
        )
        Text(
            text = "${roundedTemp}°", style = appTypography.displayLargeShadow.copy(fontWeight = FontWeight.Normal, fontSize = 72.sp), color = Color.White
        )
        Text(
            text = weather.description, style = appTypography.bodyMediumShadow, color = Color.White
        )
        Text(
            text = "Max. ${maxTemp}°  Min. ${minTemp}°",
            style = appTypography.bodyMediumShadow,
            color = Color.White
        )

    }
}

@Composable
fun CurrentLoadingView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "My location", style = appTypography.titleLargeNoShadow, color = Color.Transparent
        )
        Text(
            text = "CityName", style = appTypography.bodySmallNoShadow, color = Color.Transparent
        )
        Text(
            text = "00", style = appTypography.displayLargeNoShadow.copy(fontWeight = FontWeight.Normal, fontSize = 72.sp), color = Color.Transparent
        )
        Text(
            text = "Description", style = appTypography.bodyMediumNoShadow, color = Color.Transparent
        )
        Text(
            text = "Max. 00°  Min. 00°",
            style = appTypography.bodyMediumNoShadow,
            color = Color.Transparent
        )

    }
}