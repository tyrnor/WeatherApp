package com.example.weatherapp.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val baseTextStyle : TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)
val shadow: Shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 10f)

object AppTypography {

    val appTypography = WeatherAppTypography(
        titleLargeNoShadow = baseTextStyle.copy(fontSize = 24.sp),
        titleLargeShadow = baseTextStyle.copy(fontSize = 24.sp, shadow = shadow),
        titleMediumNoShadow = baseTextStyle,
        titleMediumShadow = baseTextStyle.copy(shadow = shadow),
        titleSmallNoShadow = baseTextStyle.copy(fontSize = 12.sp),
        titleSmallShadow = baseTextStyle.copy(fontSize = 12.sp, shadow = shadow),
        bodyLargeNoShadow = baseTextStyle.copy(fontSize = 24.sp),
        bodyLargeShadow = baseTextStyle.copy(fontSize = 24.sp, shadow = shadow),
        bodyMediumNoShadow = baseTextStyle,
        bodyMediumShadow = baseTextStyle.copy(shadow = shadow),
        bodySmallNoShadow = baseTextStyle.copy(fontSize = 12.sp),
        bodySmallShadow = baseTextStyle.copy(fontSize = 12.sp, shadow = shadow),
        displayLargeNoShadow = baseTextStyle.copy(fontSize = 24.sp),
        displayLargeShadow = baseTextStyle.copy(fontSize = 24.sp, shadow = shadow),
        displayMediumNoShadow = baseTextStyle,
        displayMediumShadow = baseTextStyle.copy(shadow = shadow),
        displaySmallNoShadow = baseTextStyle.copy(fontSize = 12.sp),
        displaySmallShadow = baseTextStyle.copy(fontSize = 12.sp, shadow = shadow)
    )
}

class WeatherAppTypography(
    val titleLargeNoShadow: TextStyle = TextStyle(),
    val titleLargeShadow: TextStyle = TextStyle(),
    val titleMediumNoShadow: TextStyle = TextStyle(),
    val titleMediumShadow: TextStyle = TextStyle(),
    val titleSmallNoShadow: TextStyle = TextStyle(),
    val titleSmallShadow: TextStyle = TextStyle(),
    val bodyLargeNoShadow: TextStyle = TextStyle(),
    val bodyLargeShadow: TextStyle = TextStyle(),
    val bodyMediumNoShadow: TextStyle = TextStyle(),
    val bodyMediumShadow: TextStyle = TextStyle(),
    val bodySmallNoShadow: TextStyle = TextStyle(),
    val bodySmallShadow: TextStyle = TextStyle(),
    val displayLargeNoShadow: TextStyle = TextStyle(),
    val displayLargeShadow: TextStyle = TextStyle(),
    val displayMediumNoShadow: TextStyle = TextStyle(),
    val displayMediumShadow: TextStyle = TextStyle(),
    val displaySmallShadow: TextStyle = TextStyle(),
    val displaySmallNoShadow: TextStyle = TextStyle(),
)