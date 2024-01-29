package com.example.weatherapp.ui.view

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.model.CurrentWeatherModel
import com.example.weatherapp.domain.model.DailyWeatherModel
import com.example.weatherapp.domain.model.HourlyWeatherInfo
import com.example.weatherapp.domain.model.HourlyWeatherModel
import com.example.weatherapp.ui.state.WeatherState
import com.example.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.math.roundToInt

private const val REQUEST_LOCATION_PERMISSION = 1

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
    }

    @Composable
    fun WeatherApp(isPermissionGranted: Boolean) {
        val currentWeather by mainViewModel.currentWeatherState.collectAsState()
        val hourlyWeather by mainViewModel.hourlyWeatherState.collectAsState()
        val dailyWeather by mainViewModel.dailyWeatherState.collectAsState()
        val cityName by mainViewModel.cityName.collectAsState()
        val canFetchData by remember { mutableStateOf(isPermissionGranted) }

        LaunchedEffect(key1 = Unit) {
            if (canFetchData) {
                mainViewModel.getCurrentWeather()
            }
        }

        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            when (currentWeather) {
                is WeatherState.Loading -> LoadingView()
                is WeatherState.Success -> CurrentWeatherView(
                    weather = (currentWeather as WeatherState.Success.CurrentWeatherSuccess).data,
                    cityName = cityName
                )

                is WeatherState.Error -> ErrorView(message = (currentWeather as WeatherState.Error).message)
            }
            when (hourlyWeather) {
                is WeatherState.Loading -> LoadingView()
                is WeatherState.Success -> HourlyWeatherView(
                    weather = (hourlyWeather as WeatherState.Success.HourlyWeatherSuccess).data
                )

                is WeatherState.Error -> ErrorView(message = (hourlyWeather as WeatherState.Error).message)
            }
            when (dailyWeather) {
                is WeatherState.Loading -> LoadingView()
                is WeatherState.Success -> DailyWeatherView(
                    weather = (dailyWeather as WeatherState.Success.DailyWeatherSuccess).data
                )

                is WeatherState.Error -> ErrorView(message = (dailyWeather as WeatherState.Error).message)
            }
        }
    }

    @Composable
    fun CurrentWeatherView(weather: CurrentWeatherModel, cityName: String) {
        val roundedTemp = weather.temperature.roundToInt()
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = cityName.uppercase(), style = MaterialTheme.typography.titleMedium)
            Text(text = "${roundedTemp}°", style = MaterialTheme.typography.headlineLarge)
            Text(text = weather.description)

        }
    }

    @Composable
    fun HourlyWeatherView(weather: HourlyWeatherModel) {
        val weatherData = weather.getCombinedWeatherInfo()

        LazyRow {
            itemsIndexed(weatherData) { index, data ->
                WeatherItemView(index, data)
            }
        }

    }

    @Composable
    fun WeatherItemView(index: Int, data: HourlyWeatherInfo) {
        val (hour, description, temp) = data
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = if (index == 0) "Now" else hour.toString(), style = MaterialTheme.typography.titleSmall)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "${temp}°", style = MaterialTheme.typography.titleMedium)
        }

    }

    @Composable
    fun DailyWeatherView(weather: DailyWeatherModel) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                weather.dayOfWeek.forEach{
                    Text(text = it)
                }
            }
            Column {
                weather.descriptions.forEach { 
                    Text(text = it)
                }
            }
            Column {
                weather.minTemperature.forEach {
                    Text(text = "${it.roundToInt()}°")
                }
            }
            Column {
                weather.maxTemperature.forEach { 
                    Text(text = "${it.roundToInt()}°")
                }
            }
        }
    }

    @Composable
    fun ErrorView(message: String) {
        Text(text = message)
    }

    @Composable
    fun LoadingView() {
    }

    private fun requestLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            setContent { WeatherApp(true) }
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your location to show the weather data.",
                REQUEST_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            setContent { WeatherApp(false) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            setContent { WeatherApp(true) }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}



