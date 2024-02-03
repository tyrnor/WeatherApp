package com.example.weatherapp.ui.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.ui.theme.AppTypography.appTypography
import com.valentinilk.shimmer.shimmer
import kotlin.math.roundToInt

@Composable
fun DailyWeatherView(weather: DailyWeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color.Transparent, shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    Color.Transparent, shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = "Forecast (7 days)".uppercase(),
                modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                style = appTypography.bodyMediumShadow.copy(fontSize = 12.sp),
                color = Color.White.copy(alpha = 0.5f)
            )
            Divider(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = Color.White.copy(alpha = 0.5f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DaysOfWeekColumn(daysOfWeek = weather.dayOfWeek)
                IconsColumn(icons = weather.icons)
                MinTemperaturesColumn(minTemperatures = weather.minTemperature)
                MaxTemperaturesColumn(maxTemperatures = weather.maxTemperature)

            }
        }
    }
}

@Composable
fun MaxTemperaturesColumn(maxTemperatures: List<Double>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        maxTemperatures.forEach { maxTemp ->
            Text(
                text = "${maxTemp.roundToInt()}°",
                modifier = Modifier.padding(bottom = 8.dp),
                style = appTypography.displayMediumShadow.copy(fontSize = 16.sp),
                color = Color.White
            )
        }
    }
}

@Composable
fun MinTemperaturesColumn(minTemperatures: List<Double>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        minTemperatures.forEach { minTemp ->
            Text(
                text = "${minTemp.roundToInt()}°",
                modifier = Modifier.padding(bottom = 8.dp),
                style = appTypography.displayMediumShadow.copy(fontSize = 16.sp),
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun IconsColumn(icons: List<Int>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        icons.forEach { icon ->
            SubcomposeAsyncImage(
                model = icon,
                loading = { CircularProgressIndicator() },
                contentDescription = "Icon",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun DaysOfWeekColumn(daysOfWeek: List<String>) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEachIndexed { index, dayOfWeek ->
            if (index == 0) {
                Text(
                    text = "Today",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = appTypography.bodyMediumShadow.copy(fontSize = 16.sp),
                    color = Color.White
                )
            } else {
                Text(
                    text = dayOfWeek,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = appTypography.bodyMediumShadow.copy(fontSize = 16.sp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DailyLoadingView() {
    Card(
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color.Transparent, shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    Color.Transparent, shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = "Forecast (7 days)".uppercase(),
                modifier = Modifier.padding(top = 16.dp, start = 24.dp),
                style = TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold
                ),
                color = Color.Transparent
            )
            Divider(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = Color.Transparent
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

            }
        }
    }
}