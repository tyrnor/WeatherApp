package com.example.weatherapp.ui.view

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.weatherapp.domain.model.DailyWeatherModel
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
                style = TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight.SemiBold, shadow = Shadow(
                        color = Color.Black, offset = Offset(3f, 3f), blurRadius = 4f
                    )
                ),
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
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    weather.dayOfWeek.forEachIndexed { index, dayOfWeek ->
                        if (index == 0) {
                            Text(
                                text = "Today",
                                modifier = Modifier.padding(bottom = 8.dp),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 3f),
                                        blurRadius = 10f
                                    )
                                ),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = dayOfWeek,
                                modifier = Modifier.padding(bottom = 8.dp),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(3f, 3f),
                                        blurRadius = 10f
                                    )
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    weather.icons.forEach { icon ->
                        SubcomposeAsyncImage(
                            model = icon,
                            loading = { CircularProgressIndicator() },
                            contentDescription = "Icon",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    weather.minTemperature.forEach { minTemp ->
                        Text(
                            text = "${minTemp.roundToInt()}°",
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 3f),
                                    blurRadius = 10f
                                )
                            ),
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    weather.maxTemperature.forEach { maxTemp ->
                        Text(
                            text = "${maxTemp.roundToInt()}°",
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 3f),
                                    blurRadius = 10f
                                )
                            ),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyLoadingView(){
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
        ){
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
            ){

            }
        }
    }
}