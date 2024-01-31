package com.example.weatherapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import coil.compose.SubcomposeAsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.HourlyWeatherInfo
import com.example.weatherapp.domain.model.HourlyWeatherModel
import com.valentinilk.shimmer.shimmer

@Composable
fun HourlyWeatherView(weather: HourlyWeatherModel) {
    val weatherData = weather.getCombinedWeatherInfo()

    Card(
        modifier = Modifier
            .padding(16.dp)
            .background(
                Color.Transparent, shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.3f))
    ) {
        LazyRow(
            modifier = Modifier.background(Color.Transparent)
        ) {
            itemsIndexed(weatherData) { index, data ->
                WeatherItemView(index, data)
            }
        }
    }


}

@Composable
fun WeatherItemView(index: Int, data: HourlyWeatherInfo) {
    val (hour, icon, temp) = data
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Text(
            text = if (index == 0) "Now" else hour.toString(), style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 4f)
            ), color = Color.White.copy(alpha = 0.5f)
        )
        SubcomposeAsyncImage(
            model = icon,
            loading = { CircularProgressIndicator() },
            contentDescription = "Icon",
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "${temp}°", style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)
            ), color = Color.White
        )
    }

}

@Composable
fun HourlyLoadingView() {
    Card(
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color.Transparent, shape = RoundedCornerShape(16.dp)
            ), colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.3f))
    ){
        LazyRow(
            modifier = Modifier.background(Color.Transparent)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text =  "Now", style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        ), color = Color.Transparent
                    )
                    SubcomposeAsyncImage(
                        model = R.drawable.clear_sky_ic,
                        loading = { CircularProgressIndicator() },
                        contentDescription = "Icon",
                        modifier = Modifier.size(48.dp),
                        alpha = 0f
                    )
                    Text(
                        text = "00°", style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        ), color = Color.Transparent
                    )
                }
            }
        }
    }
}